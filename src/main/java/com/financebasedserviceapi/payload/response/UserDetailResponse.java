package com.financebasedserviceapi.payload.response;

import com.financebasedserviceapi.enums.AccountCurrency;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailResponse {
    String userName;
    String name;
    String email;
    String accountNumber;
    AccountCurrency accountCurrency;
}
