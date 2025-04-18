package com.ashoumar.accounts.service;

import com.ashoumar.accounts.dto.CustomerDto;

public interface IAccountsService {


    void createAccount(CustomerDto customerDto);

    CustomerDto fetAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
