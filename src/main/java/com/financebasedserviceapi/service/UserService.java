package com.financebasedserviceapi.service;

import com.financebasedserviceapi.payload.request.SignInRequest;
import com.financebasedserviceapi.payload.request.SignUpRequest;
import com.financebasedserviceapi.payload.response.UserDetailResponse;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    void registerUser(SignUpRequest request);

    void loginUser(SignInRequest request);

    UserDetailResponse getUserDetail();
}
