package com.project.flowbox_backend.Customers.adapters.in.web;

import org.springframework.stereotype.Component;

import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;

@Component
public class CustomerMapper {

    public static  CustomerDTO toDTO(Customer customer){
        return new CustomerDTO (
            customer.getDni(),
            customer.getNombre(),
            customer.getApellido(),
            customer.getTelefono()
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO){
        Customer customer = new Customer();
        customer.setDni(customerDTO.getdni());
        customer.setNombre(customerDTO.getNombre());
        customer.setApellido(customerDTO.getApellido());
        customer.setTelefono(customerDTO.getTelefono());
        return customer;
    }
}
