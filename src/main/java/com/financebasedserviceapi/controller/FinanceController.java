package com.financebasedserviceapi.controller;

import com.financebasedserviceapi.payload.request.FundAccountRequest;
import com.financebasedserviceapi.payload.request.SignInRequest;
import com.financebasedserviceapi.payload.request.SignUpRequest;
import com.financebasedserviceapi.payload.request.TransferRequest;
import com.financebasedserviceapi.payload.response.AccountDetailResponse;
import com.financebasedserviceapi.payload.response.FundAccountResponse;
import com.financebasedserviceapi.payload.response.SignInResponse;
import com.financebasedserviceapi.payload.response.SignUpResponse;
import com.financebasedserviceapi.payload.response.TransactionHistoryResponse;
import com.financebasedserviceapi.payload.response.TransferResponse;
import com.financebasedserviceapi.payload.response.UserDetailResponse;
import com.financebasedserviceapi.service.AccountService;
import com.financebasedserviceapi.service.TransactionService;
import com.financebasedserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="api/v1")
@Validated
public class FinanceController {
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    @PostMapping(path = "/register", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<SignUpResponse> registerUser(@Valid @RequestBody SignUpRequest request){
        userService.registerUser(request);
        SignUpResponse signUpResponse = SignUpResponse.builder()
                .createdAt(LocalDateTime.now())
                .status("Registration Successful")
                .build();
        return new ResponseEntity<>(signUpResponse, HttpStatus.CREATED);
    }

    @PostMapping(path = "/login", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<SignInResponse> loginUser(@Valid @RequestBody SignInRequest request){
        userService.loginUser(request);
        SignInResponse signInResponse = SignInResponse.builder()
                .createdAt(LocalDateTime.now())
                .status("Login Successful")
                .build();
        return new ResponseEntity<>(signInResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/user-detail", produces = "application/json")
    public ResponseEntity<UserDetailResponse> getUserDetail(){
        return new ResponseEntity<>(userService.getUserDetail(), HttpStatus.OK);
    }

    @PostMapping(path = "/fund-account")
    public ResponseEntity<FundAccountResponse> fundAccount (@Valid @RequestBody FundAccountRequest request){
        accountService.fundAccount(request);
        FundAccountResponse fundAccountResponse = FundAccountResponse.builder()
                .createdAt(LocalDateTime.now())
                .status("Success")
                .build();
        return new ResponseEntity<>(fundAccountResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/account-balance", produces = "application/json")
    public ResponseEntity<AccountDetailResponse> getAccountBalance(){
        return new ResponseEntity<>(accountService.getAccountBalance(), HttpStatus.OK);
    }

    @PostMapping(path = "/transfer", consumes = {MediaType.ALL_VALUE}, produces = "application/json")
    public ResponseEntity<TransferResponse> makeTransfer (@Valid @RequestBody TransferRequest request){
        transactionService.makeTransfer(request);
        TransferResponse transferResponse = TransferResponse.builder()
                .timeframes(LocalDateTime.now())
                .status("Success")
                .build();
        return  new ResponseEntity<>(transferResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/transaction-history", produces = "application/json")
    public ResponseEntity<List<TransactionHistoryResponse>> getAllTransactionHistory(){
        return new ResponseEntity<>(transactionService.getTransactionHistory(), HttpStatus.OK);
    }

}
