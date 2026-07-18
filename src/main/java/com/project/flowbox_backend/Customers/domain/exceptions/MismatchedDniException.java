package com.project.flowbox_backend.Customers.domain.exceptions;

public class MismatchedDniException extends RuntimeException {
    public MismatchedDniException(String message) {
        super(message);
    }
}
