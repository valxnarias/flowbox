package com.project.flowbox_backend.Customers.adapters.in.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.project.flowbox_backend.Customers.Services.*;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
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

    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable @NotNull(message = "El Dni no puede ser null") @NotBlank(message = "El Dni no puede estar vacio") @Pattern(regexp = "^[0-9]{8}$", message = "El Dni debe tener 8 digitos") String dni) {
        customerService.delete(dni);
        return ResponseEntity.noContent().build();
    }

}
