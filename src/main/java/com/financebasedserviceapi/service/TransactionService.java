package com.financebasedserviceapi.service;

import com.financebasedserviceapi.payload.request.TransferRequest;
import com.financebasedserviceapi.payload.response.TransactionHistoryResponse;

import java.util.List;

public interface TransactionService {

    void makeTransfer(TransferRequest request);

    List<TransactionHistoryResponse> getTransactionHistory();
}
