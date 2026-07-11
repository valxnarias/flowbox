package com.project.flowbox_backend.Customers.ports.out.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void testSaveAndFindByDni() {
        // Arrange: crear una entidad customer
        Customer customer = new Customer("12345678", "Juan", "Perez", "1234567890");
        // Act: guardar el cliente
        repository.save(customer);
        // Assert: buscar el cliente por dni y cerificar que coincida todo
        Optional<Customer> found = repository.findById("12345678");
        assertTrue(found.isPresent());
        assertEquals("Juan", found.get().getNombre());
        assertEquals("Perez", found.get().getApellido());
        assertEquals("1234567890", found.get().getTelefono());
    }
}
