package com.financebasedserviceapi.exceptions;

public class IncorrectLoginDetailsException extends RuntimeException {
    public IncorrectLoginDetailsException(String message) {
        super(message);
    }
}
