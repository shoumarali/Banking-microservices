package com.ashoumar.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to hold Account information"
)
public class AccountsDto {

    @Schema(description = "Account number for bank account")
    @NotEmpty(message = "AccountNumber cannot be null or empty")
    @Pattern(regexp = "^[0-9]{8,}$", message = "Mobile number must be at least 8 digits")
    private Long accountNumber;

    @Schema(description = "Account type of the bank account", example = "Savings")
    @NotEmpty(message = "AccountType cannot be null or empty")
    private String accountType;

    @Schema(description = "Branch Address of the bank account")
    @NotEmpty(message = "BranchAddress cannot be null or empty")
    private String branchAddress;
}
