package com.project.flowbox_backend.Customers.adapters.in.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;

import jakarta.validation.Valid;

import com.project.flowbox_backend.Customers.Services.*;

@RestController
@RequestMapping("/api/v1/customers")
class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.create(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getCustomers(@RequestParam(required = false) String search) {
        return ResponseEntity.ok(customerService.findAll(Optional.ofNullable(search)));
    }
}
