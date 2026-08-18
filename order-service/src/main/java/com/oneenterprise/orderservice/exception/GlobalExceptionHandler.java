package com.oneenterprise.orderservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 — the order itself doesn't exist
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(OrderNotFoundException ex) {
        ErrorResponse body = new ErrorResponse("ORDER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 404 — the order exists, but the user it references doesn't exist in User Service
    @ExceptionHandler(RelatedUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRelatedUserNotFound(RelatedUserNotFoundException ex) {
        ErrorResponse body = new ErrorResponse("RELATED_USER_NOT_FOUND", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 503 — User Service could not be reached at all (down, timed out, etc.)
    // This is the case the handbook wants you to *see* by stopping User Service.
    @ExceptionHandler(UserServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceUnavailable(UserServiceUnavailableException ex) {
        ErrorResponse body = new ErrorResponse(
                "USER_SERVICE_UNAVAILABLE",
                "Order Service could not reach User Service. " + ex.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE.value());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(body);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleBadId(MethodArgumentTypeMismatchException ex) {
        ErrorResponse body = new ErrorResponse(
                "INVALID_REQUEST",
                "The order id must be a valid number.",
                HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        ErrorResponse body = new ErrorResponse(
                "INTERNAL_ERROR",
                "Something went wrong while processing the request.",
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
