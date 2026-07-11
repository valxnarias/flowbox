package com.project.flowbox_backend.Customers.Services;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.project.flowbox_backend.Customers.adapters.in.web.CustomerMapper;
import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;
import com.project.flowbox_backend.Customers.ports.out.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    
    public CustomerService (CustomerRepository repository){
        this.repository = repository;
    }

    public void create(CustomerDTO customerDTO) {
        // Lógica para crear un cliente
        // Convertimos el DTO a Entidad
        Customer entity = CustomerMapper.toEntity(customerDTO);
        
        // Le decimos a Java: "Asegúrate de que esto no sea nulo, y si lo es, lanza un error claro"
        Objects.requireNonNull(entity, "La entidad a guardar no puede ser nula");
        
        // Ahora el IDE sabe que aquí 'entity' es 100% seguro
        repository.save(entity);
    }
}
