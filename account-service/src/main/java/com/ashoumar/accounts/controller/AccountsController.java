package com.ashoumar.accounts.controller;

import com.ashoumar.accounts.constants.AccountsConstant;
import com.ashoumar.accounts.dto.CustomerDto;
import com.ashoumar.accounts.dto.ResponseDto;
import com.ashoumar.accounts.entity.Customer;
import com.ashoumar.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@RestController it’s a shortcut for @Controller + @ResponseBody.
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountsConstant.STATUS_201,
                        AccountsConstant.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> getAccount(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber){
        CustomerDto customerDto = iAccountsService.fetAccount(mobileNumber);

        return ResponseEntity
                .status(200)
                .body(customerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto){
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if(isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstant.STATUS_200,
                            AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(
                            AccountsConstant.STATUS_500,
                            AccountsConstant.MESSAGE_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber){
        if(iAccountsService.deleteAccount(mobileNumber)){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstant.STATUS_200,
                            AccountsConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(
                            AccountsConstant.STATUS_500,
                            AccountsConstant.MESSAGE_500));
        }
    }
}
