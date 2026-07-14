package com.project.flowbox_backend.Customers.adapters.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.flowbox_backend.Customers.domain.exceptions.CustomerAlreadyExistsException;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleCustomerAlreadyExistsException(CustomerAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleRuntimeException(RuntimeException ex) {
        return "Ocurrió un error inesperado en el servidor: " + ex.getMessage();
    }

}
