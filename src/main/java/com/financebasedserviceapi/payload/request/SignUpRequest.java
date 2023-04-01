package com.financebasedserviceapi.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpRequest {
    @NotBlank(message = "user name should not be empty")
    String userName;

    @NotBlank(message = "first name should not be empty")
    String firstName;

    @NotBlank(message = "last name should not be empty")
    String lastName;

    @Email
    @NotBlank(message = "email should not be empty")
    String email;

    @NotBlank(message = "password should not be empty")
    String password;

    @NotBlank(message = "Account currency should not be empty")
    @Pattern(regexp = "[AB]", message = "Only currency A and B is accepted")
    String accountCurrency;
}
