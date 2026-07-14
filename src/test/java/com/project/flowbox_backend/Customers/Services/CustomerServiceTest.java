package com.project.flowbox_backend.Customers.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.mockito.junit.jupiter.MockitoExtension;

import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;
import com.project.flowbox_backend.Customers.domain.exceptions.CustomerAlreadyExistsException;
import com.project.flowbox_backend.Customers.ports.out.persistence.CustomerRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    // test para endpoint POST /api/v1/customers
    @Test
    public void testCreateCustomer_SavesToData() {
        // Arrange: preparar los datos de entrada
        CustomerDTO customerDTO = new CustomerDTO("12345678", "John", "Doe", "1234567890");

        // Act: Ejecutar el metodo real del servicio
        customerService.create(customerDTO);

        // Creamos nuestra "red" diseñada específicamente para atrapar objetos Customer
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        // Le decimos al mock que capture lo que le intentaron pasar al método save()
        verify(customerRepository).save(captor.capture());

        // Sacamos a nuestro cliente de la red para inspeccionarlo
        Customer savedCustomer = captor.getValue();

        // ¡Verificamos que el Mapper haya hecho su trabajo a la perfección!
        assertEquals("12345678", savedCustomer.getDni());
        assertEquals("John", savedCustomer.getNombre());
        assertEquals("Doe", savedCustomer.getApellido());
        assertEquals("1234567890", savedCustomer.getTelefono());
    }

    @Test
    public void testCustomerAlreadyExists_ThrowsException() {
        // 1. Arrange: Simulamos que el repositorio ya tiene ese DNI registrado
        when(customerRepository.existsById("12345678")).thenReturn(true);
        // Arrange: preparar los datos de entrada
        CustomerDTO customerDTO = new CustomerDTO("12345678", "John", "Doe", "1234567890");
        // 2. Act & Assert: Verificamos que al llamar a crear, lance la excepción
        assertThrows(CustomerAlreadyExistsException.class, () -> {
            customerService.create(customerDTO);
        });
    }

    @Test
    public void testFindAllCustomers_ReturnsAllCustomers() {
        // 1. Arrange: Simulamos que el repositorio trae 2 clientes
        List<Customer> mockCustomers = Arrays.asList(
                new Customer("12345678", "John", "Doe", "1234567890"),
                new Customer("87654321", "Jane", "Doe", "0987654321"));
        when(customerRepository.findAll()).thenReturn(mockCustomers);

        // 2. Act & Assert: Ejecutamos el método y validamos el resultado
        List<CustomerDTO> result = customerService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getNombre());
        assertEquals("Doe", result.get(0).getApellido());
        assertEquals("12345678", result.get(0).getDni());
        assertEquals("1234567890", result.get(0).getTelefono());
        assertEquals("Jane", result.get(1).getNombre());
        assertEquals("Doe", result.get(1).getApellido());
        assertEquals("87654321", result.get(1).getDni());
        assertEquals("0987654321", result.get(1).getTelefono());
        verify(customerRepository).findAll();
    }

    @Test
    public void testFindAll_NoCustomers_ReturnsEmptyList() {
        // Arrange: El repositorio devuelve una lista vacía
        when(customerRepository.findAll()).thenReturn(java.util.Collections.emptyList());

        // Act
        List<CustomerDTO> result = customerService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository).findAll();
    }

}
