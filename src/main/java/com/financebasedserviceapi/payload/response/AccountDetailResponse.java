package com.financebasedserviceapi.payload.response;

import com.financebasedserviceapi.enums.AccountCurrency;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetailResponse {
    String accountName;
    String accountNumber;
    String totalAccountBalance;
}
