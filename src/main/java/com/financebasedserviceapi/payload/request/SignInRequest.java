package com.financebasedserviceapi.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
    @NotBlank(message = "user name should not be empty")
    String userName;
    @NotBlank(message = "password should not be empty")
    String password;
}
