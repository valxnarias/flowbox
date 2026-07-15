package com.project.flowbox_backend.Customers.ports.out.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Query("""
            SELECT c FROM Customer c
            WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.apellido) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.dni) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(c.telefono) LIKE LOWER(CONCAT('%', :search, '%'))
            """)
    List<Customer> searchGlobal(@Param("search") String search);

}
