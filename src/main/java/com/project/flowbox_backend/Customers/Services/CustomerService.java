package com.project.flowbox_backend.Customers.Services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.flowbox_backend.Customers.adapters.in.web.CustomerMapper;
import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;
import com.project.flowbox_backend.Customers.domain.exceptions.CustomerAlreadyExistsException;
import com.project.flowbox_backend.Customers.ports.out.persistence.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void create(CustomerDTO customerDTO) {

        // Verificar que el DTO no es null
        Objects.requireNonNull(customerDTO, "El DTO no puede ser nulo");

        // Guardar el DNI y validar que no sea null
        String dni = Objects.requireNonNull(customerDTO.getDni(), "El DNI no puede ser nulo");

        // Validamos que el cliente no exista
        if (repository.existsById(dni)) {
            throw new CustomerAlreadyExistsException("El cliente con DNI " + dni + " ya existe.");
        }

        // Convertimos el DTO a Entidad
        Customer entity = CustomerMapper.toEntity(customerDTO);

        // Le decimos a Java: "Asegúrate de que esto no sea nulo, y si lo es, lanza un
        // error claro"
        Objects.requireNonNull(entity, "La entidad a guardar no puede ser nula");

        // Ahora el IDE sabe que aquí 'entity' es 100% seguro
        repository.save(entity);
    }

    public List<CustomerDTO> findAll() {
        return repository.findAll().stream()
                .map(CustomerMapper::toDTO)
                .collect(Collectors.toList());
    }
}
