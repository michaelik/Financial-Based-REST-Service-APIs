package com.financebasedserviceapi.exceptions;

public class IllegalTransferException extends RuntimeException {
    public IllegalTransferException(String message) {
        super(message);
    }
}
