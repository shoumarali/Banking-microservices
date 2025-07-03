package com.ashoumar.accounts.service.impl;

import com.ashoumar.accounts.dto.*;
import com.ashoumar.accounts.entity.Accounts;
import com.ashoumar.accounts.entity.Customer;
import com.ashoumar.accounts.exception.ResourceNotFoundException;
import com.ashoumar.accounts.mapper.AccountsMapper;
import com.ashoumar.accounts.mapper.CustomerMapper;
import com.ashoumar.accounts.repository.AccountsRepository;
import com.ashoumar.accounts.repository.CustomerRepository;
import com.ashoumar.accounts.service.ICustomerService;
import com.ashoumar.accounts.service.client.CardsFeignClient;
import com.ashoumar.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private LoansFeignClient loansFeignClient;
    private CardsFeignClient cardsFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()  -> new ResourceNotFoundException("Accounts","accountId",customer.getCustomerId().toString())
        );

        CustomerDetailsDto customerDetailsDto = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());

        customerDetailsDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        ResponseEntity<LoansDto> loansDto = loansFeignClient.fetchLoanDetails(correlationId ,mobileNumber);
        customerDetailsDto.setLoansDto(loansDto.getBody());

        ResponseEntity<CardsDto> cardsDto = cardsFeignClient.fetchCardDetails(correlationId,mobileNumber);
        customerDetailsDto.setCardsDto(cardsDto.getBody());

        return customerDetailsDto;
    }


}
