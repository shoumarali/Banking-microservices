package com.ashoumar.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer Details",
        description = "Schema to hold all information's related to customer"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer",
            example = "ali"
    )
    @NotEmpty(message = "Name cannot be null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "email of the customer",
            example = "ali@gmail.com"
    )
    @NotEmpty(message = "Email cannot be null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile number of the customer",
            example = "88888888"
    )
    @NotEmpty(message = "MobileNumber cannot be null or empty")
    @Pattern(regexp = "^[0-9]{8}$", message = "Mobile number must be exactly 8 digits")
    private String mobileNumber;

    @Schema(description = "Account details of the customer")
    private AccountsDto accountsDto;

    @Schema(description = "cards details of the customer")
    private CardsDto cardsDto;

    @Schema(description = "loans details of the customer")
    private LoansDto loansDto;

}
