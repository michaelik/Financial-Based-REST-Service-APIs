package com.financebasedserviceapi.service.implementation;

import com.financebasedserviceapi.enums.AccountCurrency;
import com.financebasedserviceapi.exceptions.AccountNotFoundException;
import com.financebasedserviceapi.exceptions.IllegalTransferException;
import com.financebasedserviceapi.exceptions.InsufficientBalanceException;
import com.financebasedserviceapi.exceptions.TransactionFailedException;
import com.financebasedserviceapi.exceptions.UserNotFoundException;
import com.financebasedserviceapi.exceptions.UserNotLoggedInException;
import com.financebasedserviceapi.model.Account;
import com.financebasedserviceapi.model.Transaction;
import com.financebasedserviceapi.model.User;
import com.financebasedserviceapi.payload.request.TransferRequest;
import com.financebasedserviceapi.payload.response.TransactionHistoryResponse;
import com.financebasedserviceapi.repository.AccountRepository;
import com.financebasedserviceapi.repository.TransactionRepository;
import com.financebasedserviceapi.repository.UserRepository;
import com.financebasedserviceapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.financebasedserviceapi.enums.AccountCurrency.A;
import static com.financebasedserviceapi.enums.AccountCurrency.B;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private  final TransactionRepository transactionRepository;
    private final HttpSession httpSession;

    @Override
    public void makeTransfer(TransferRequest request) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null)
            throw new UserNotLoggedInException("Access to the requested resource is denied due to authentication failure");
        User debit = userRepository.findById(userId).orElseThrow(()-> new UserNotLoggedInException("User not Found"));
        Account debitAccount = accountRepository.findAccountByUser(debit);

        Account recipientAccount = accountRepository
                .findAccountByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found"));
        User recipient = Optional.ofNullable(recipientAccount.getUser()).orElseThrow(()-> new UserNotFoundException("Recipient Not Found"));

        if (recipient.equals(debit)){
            throw new IllegalTransferException("Can not transfer to self");
        }
        if (debitAccount.getBalance().compareTo(request.getAmount()) <= 0) {
            throw new InsufficientBalanceException("Insufficient Funds");
        }
        BigDecimal amount = currencyToCurrencyRate(request.getAmount(),
                                                   debitAccount.getCurrency(),
                                                   recipientAccount.getCurrency());
        saveTransactionDetail(debitAccount.getAccountNumber(), recipient.getFirstName(),
                              recipient.getLastName(), recipientAccount.getAccountNumber(),
                              request.getAmount(), amount, debitAccount.getBalance(),
                              debit, debitAccount, recipientAccount.getBalance(), recipient,
                              recipientAccount);
        try {
            debitAccount.setBalance(debitAccount.getBalance().subtract(request.getAmount()));
            recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
            accountRepository.save(debitAccount);
            accountRepository.save(recipientAccount);
        } catch (Exception e) {
            throw new TransactionFailedException("Transaction Failed");
        }
    }

    @Override
    public List<TransactionHistoryResponse> getTransactionHistory() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null)
            throw new UserNotLoggedInException("Access to the requested resource is denied due to authentication failure");
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotLoggedInException("User not Found"));
        Account debitAccount = accountRepository.findAccountByUser(user);
        List<Transaction> transactions = transactionRepository.findAllByAccount(debitAccount);
        List<TransactionHistoryResponse> response = new ArrayList<>();
        for(Transaction transaction : transactions){
            AccountCurrency currency = transaction.getAccount().getCurrency();
            response.add(TransactionHistoryResponse.builder()
                            .transactionId(transaction.getTransactionId())
                            .debitAccount(transaction.getDebitAccount())
                            .recipient(transaction.getRecipientFirstName()+ " " +
                                       transaction.getRecipientLastName() + " | " +
                                       transaction.getRecipientAccount())
                            .amount(currency+""+transaction.getAmount())
                            .balance(currency+""+transaction.getCurrentBalance())
                            .timestamp(transaction.getTimestamp())
                            .build());
        }
        return response;
    }

    private BigDecimal currencyToCurrencyRate(BigDecimal debitAmount,
                                             AccountCurrency debitCurrency,
                                             AccountCurrency recipientCurrency){
        BigDecimal amount = null;
        if (debitCurrency.equals(A) && recipientCurrency.equals(B))
        {
            amount =  debitAmount.multiply(BigDecimal.valueOf(1.3455), MathContext.DECIMAL32);
        } else if (debitCurrency.equals(B) && recipientCurrency.equals(A))
        {
            amount = debitAmount.divide(BigDecimal.valueOf(1.3455), MathContext.DECIMAL32);
        }
        return amount;
    }

    private void saveTransactionDetail(String debitAccountNumber,
                                       String recipientFirstName,
                                       String recipientLastName,
                                       String recipientAccount,
                                       BigDecimal requestAmount,
                                       BigDecimal amount,
                                       BigDecimal debitBalance,
                                       User debitUser,
                                       Account debitAccount,
                                       BigDecimal creditBalance,
                                       User creditUser,
                                       Account creditAccount){
        Transaction debit = Transaction.builder()
                .debitAccount(debitAccountNumber)
                .recipientFirstName(recipientFirstName)
                .recipientLastName(recipientLastName)
                .recipientAccount(recipientAccount)
                .amount(requestAmount)
                .currentBalance(debitBalance)
                .user(debitUser)
                .account(debitAccount)
                .build();
        Transaction credit = Transaction.builder()
                .debitAccount(debitAccountNumber)
                .recipientFirstName(recipientFirstName)
                .recipientLastName(recipientLastName)
                .recipientAccount(recipientAccount)
                .amount(amount)
                .currentBalance(creditBalance)
                .user(creditUser)
                .account(creditAccount)
                .build();
        transactionRepository.save(debit);
        transactionRepository.save(credit);
    }
}
