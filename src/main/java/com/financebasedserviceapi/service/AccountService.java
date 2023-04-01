package com.financebasedserviceapi.service;

import com.financebasedserviceapi.enums.AccountCurrency;
import com.financebasedserviceapi.model.Account;
import com.financebasedserviceapi.payload.request.FundAccountRequest;
import com.financebasedserviceapi.payload.response.AccountDetailResponse;

public interface AccountService {

    Account createAccount(AccountCurrency currency);

    void fundAccount(FundAccountRequest request);

    AccountDetailResponse getAccountBalance();

}
