package com.project.flowbox_backend.Customers.ports.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

}
