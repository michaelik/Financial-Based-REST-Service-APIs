package com.financebasedserviceapi.exceptions.advice;

import com.financebasedserviceapi.exceptions.AccountNotFoundException;
import com.financebasedserviceapi.exceptions.EmailAlreadyTakenException;
import com.financebasedserviceapi.exceptions.IllegalTransferException;
import com.financebasedserviceapi.exceptions.IncorrectLoginDetailsException;
import com.financebasedserviceapi.exceptions.InsufficientBalanceException;
import com.financebasedserviceapi.exceptions.InvalidAmountException;
import com.financebasedserviceapi.exceptions.TransactionFailedException;
import com.financebasedserviceapi.exceptions.UserNotFoundException;
import com.financebasedserviceapi.exceptions.UserNotLoggedInException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
                                     HttpRequestMethodNotSupportedException ex,
                                     HttpHeaders headers,
                                     HttpStatus status,
                                     WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("errors", error);
        return ResponseEntity.status(status).
                body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
                                     MethodArgumentNotValidException ex,
                                     HttpHeaders headers,
                                     HttpStatus status,
                                     WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get all errors
        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .collect(Collectors.toList());
        body.put("errors", errors);
        return ResponseEntity.status(status).
                body(body);
    }

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ResponseEntity<Object> handleEmailAlreadyTakenException(final EmailAlreadyTakenException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                .body(Optional.of(body));
    }

    @ExceptionHandler(IncorrectLoginDetailsException.class)
    public ResponseEntity<Object> handleIncorrectLoginDetailsException(
                                                 final IncorrectLoginDetailsException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("errors", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
                                                 final UserNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(body);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFoundException(
                                                 final AccountNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).
                body(body);
    }

    @ExceptionHandler(IllegalTransferException.class)
    public ResponseEntity<Object> handleIllegalTransferException(
                                                 final IllegalTransferException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(body);
    }
    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<Object> handleInvalidAmountException(
                                                 final InvalidAmountException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(body);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceException(
                                                 final InsufficientBalanceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(body);
    }

    @ExceptionHandler(TransactionFailedException.class)
    public ResponseEntity<Object> handleTransactionFailedException(
                                                 final TransactionFailedException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).
                body(body);
    }

    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<Object> handleUserNotLoggedInException(
                                                 final UserNotLoggedInException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());

        // get error
        String error = ex.getMessage();
        body.put("error", error);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                body(body);
    }
}
