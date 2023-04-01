package com.financebasedserviceapi.payload.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpResponse {
    LocalDateTime createdAt;
    String status;
}
