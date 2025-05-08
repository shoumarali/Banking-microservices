package com.ashoumar.accounts.controller;

import com.ashoumar.accounts.constants.AccountsConstant;
import com.ashoumar.accounts.dto.CustomerDto;
import com.ashoumar.accounts.dto.ErrorResponseDto;
import com.ashoumar.accounts.dto.ResponseDto;
import com.ashoumar.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@RestController it’s a shortcut for @Controller + @ResponseBody.
@Tag(
        name = "Crust Rest Api for account service"

)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

    private final Environment environment;

    @Value("${build.version}")
    private String buildVersion;

    @Operation(
            summary = "Create Account Rest api",
            description = "Api to create new customer and account"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status code OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto){
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountsConstant.STATUS_201,
                        AccountsConstant.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Accounts Details Rest Api",
            description = "Rest api to fetch customer and accounts details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status code OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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

    @Operation(
            summary = "Update accounts details rest api",
            description = "Rest api to update customer and account details based on account number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status code OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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
                            AccountsConstant.STATUS_417,
                            AccountsConstant.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete accounts and customer details operation",
            description = "Rest Api to delete account and customer details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
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
                            AccountsConstant.STATUS_417,
                            AccountsConstant.MESSAGE_417_DELETE));
        }
    }

    @Operation(
            summary = "Fetch build information",
            description = "Fetch build information that is deployed into account server"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status code OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }


    @Operation(
            summary = "Fetch Java version",
            description = "Fetch Java versions details that is installed into account server"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Http status code OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Http status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("JAVA_HOME"));
    }
}
