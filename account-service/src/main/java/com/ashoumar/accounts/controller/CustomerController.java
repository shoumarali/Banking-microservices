package com.ashoumar.accounts.controller;

import com.ashoumar.accounts.dto.CustomerDetailsDto;
import com.ashoumar.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Tag(
        name = "Rest Api for customers"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@AllArgsConstructor
public class CustomerController {


    private ICustomerService customerService;

    @GetMapping("/fetch-customer-details")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestParam("mobileNumber")
            @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
            String mobileNumber
    ){

        CustomerDetailsDto customerDetailsDto = customerService.fetchCustomerDetails(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDetailsDto);

    }
}
