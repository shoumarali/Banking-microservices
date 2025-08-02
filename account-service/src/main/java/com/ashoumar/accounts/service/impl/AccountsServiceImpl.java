package com.ashoumar.accounts.service.impl;

import com.ashoumar.accounts.constants.AccountsConstant;
import com.ashoumar.accounts.dto.AccountsDto;
import com.ashoumar.accounts.dto.AccountsMsgDto;
import com.ashoumar.accounts.dto.CustomerDto;
import com.ashoumar.accounts.entity.Accounts;
import com.ashoumar.accounts.entity.Customer;
import com.ashoumar.accounts.exception.CustomerAlreadyExistException;
import com.ashoumar.accounts.exception.ResourceNotFoundException;
import com.ashoumar.accounts.mapper.AccountsMapper;
import com.ashoumar.accounts.mapper.CustomerMapper;
import com.ashoumar.accounts.repository.AccountsRepository;
import com.ashoumar.accounts.repository.CustomerRepository;
import com.ashoumar.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private static Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private final StreamBridge streamBridge;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto,new Customer());

        Optional<Customer> optionalCustomer = customerRepository
                .findByMobileNumber(customer.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistException(
                    "Customer Already registered with the given mobile number "+ customer.getMobileNumber()
            );
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
        sendCommunication(savedAccount,savedCustomer);
    }


    private void sendCommunication(Accounts account, Customer customer){
        var accountMsgDto = new AccountsMsgDto(
                account.getAccountNumber(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobileNumber()
        );
        logger.info("Sending communication request for the details in {}", accountMsgDto);
        var result = streamBridge.send("sendCommunication-out-0",accountMsgDto);
        logger.info("Is the communication request successfully processed? {}", result);
    }

    private Accounts createNewAccount(Customer customer){
        long randomAccountNumber = ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);

        Accounts account= new Accounts(
                customer.getCustomerId(),
                randomAccountNumber,
                AccountsConstant.SAVINGS,
                AccountsConstant.ADDRESS
        );

        return account;
    }

    @Override
    public CustomerDto fetAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account","customerId",customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account","AccountNumber",accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer","CustomerId",customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","MobileNumber",mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
