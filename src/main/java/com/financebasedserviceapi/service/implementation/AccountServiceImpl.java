package com.financebasedserviceapi.service.implementation;

import com.financebasedserviceapi.enums.AccountCurrency;
import com.financebasedserviceapi.exceptions.InvalidAmountException;
import com.financebasedserviceapi.exceptions.UserNotLoggedInException;
import com.financebasedserviceapi.model.Account;
import com.financebasedserviceapi.model.User;
import com.financebasedserviceapi.payload.request.FundAccountRequest;
import com.financebasedserviceapi.payload.response.AccountDetailResponse;
import com.financebasedserviceapi.repository.AccountRepository;
import com.financebasedserviceapi.repository.UserRepository;
import com.financebasedserviceapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository  userRepository;
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @Override
    public Account createAccount(AccountCurrency accountCurrency) {
        String generatedAN = generateTenDigitAccountNumber();
        if(accountRepository.existsByAccountNumber(generatedAN))
            generatedAN = generateTenDigitAccountNumber();
        return Account.builder()
                .accountNumber(generatedAN)
                .balance(BigDecimal.valueOf(0.00))
                .currency(accountCurrency)
                .build();
    }

    @Override
    public void fundAccount(FundAccountRequest request) {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null)
            throw new UserNotLoggedInException("Access to the requested resource is denied due to authentication failure");
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotLoggedInException("User not Found"));
        if (request.getAmount().compareTo(BigDecimal.ONE) < 0)
            throw new InvalidAmountException("Invalid amount");
        Account account = accountRepository.findAccountByUser(user);
        BigDecimal amount = account.getBalance().add(request.getAmount());
        account.setBalance(amount);
        accountRepository.save(account);
    }

    @Override
    public AccountDetailResponse getAccountBalance() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null)
            throw new UserNotLoggedInException("Access to the requested resource is denied due to authentication failure");
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotLoggedInException("User not Found"));
        Account account = accountRepository.findAccountByUser(user);
        return AccountDetailResponse.builder()
                .accountName(user.getFirstName() + " " + user.getLastName())
                .accountNumber(account.getAccountNumber())
                .totalAccountBalance(account.getCurrency() + "" + account.getBalance())
                .build();
    }

    private String generateTenDigitAccountNumber(){
        return IntStream.generate(() -> new Random().nextInt(10))
                .limit(10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
