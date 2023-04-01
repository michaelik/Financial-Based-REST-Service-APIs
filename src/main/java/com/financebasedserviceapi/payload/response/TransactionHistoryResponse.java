package com.financebasedserviceapi.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionHistoryResponse {
    Long transactionId;
    String debitAccount;
    String recipient;
    String amount;
    String balance;
    LocalDateTime timestamp;
}
