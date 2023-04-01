package com.financebasedserviceapi.service.implementation;

import com.financebasedserviceapi.enums.AccountCurrency;
import com.financebasedserviceapi.exceptions.EmailAlreadyTakenException;
import com.financebasedserviceapi.exceptions.IncorrectLoginDetailsException;
import com.financebasedserviceapi.exceptions.UserNotLoggedInException;
import com.financebasedserviceapi.model.Account;
import com.financebasedserviceapi.model.User;
import com.financebasedserviceapi.payload.request.SignInRequest;
import com.financebasedserviceapi.payload.request.SignUpRequest;
import com.financebasedserviceapi.payload.response.UserDetailResponse;
import com.financebasedserviceapi.repository.AccountRepository;
import com.financebasedserviceapi.repository.UserRepository;
import com.financebasedserviceapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountServiceImpl accountService;
    private final AccountRepository accountRepository;
    private  final HttpSession httpSession;

    @Override
    public void registerUser(SignUpRequest request){
        boolean userEmailExist = userRepository
                                 .findByEmail(request.getEmail())
                                 .isPresent();
        if (userEmailExist) throw new EmailAlreadyTakenException("Email Already Taken");
        User user = User.builder()
                .userName(request.getUserName())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        AccountCurrency accountCurrency = AccountCurrency.valueOf(request.getAccountCurrency());
        Account account = accountService.createAccount(accountCurrency);
        account.setUser(user);
        user.setAccount(account);
        userRepository.save(user);
        accountRepository.save(account);
    }

    @Override
    public void loginUser(SignInRequest request) {
        User user = userRepository.findByUserNameAndPassword(request.getUserName(), request.getPassword())
                .orElseThrow(() -> new IncorrectLoginDetailsException("Incorrect Login details provided"));
        httpSession.setAttribute("userId", user.getUserId());
    }

    @Override
    public UserDetailResponse getUserDetail() {
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null)
            throw new UserNotLoggedInException("Access to the requested resource is denied due to authentication failure");
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotLoggedInException("User not Found"));
        return UserDetailResponse.builder()
                .userName(user.getUserName())
                .name(user.getFirstName()+" "+user.getLastName())
                .email(user.getEmail())
                .accountNumber(user.getAccount().getAccountNumber())
                .accountCurrency(user.getAccount().getCurrency())
                .build();
    }
}

