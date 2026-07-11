package com.project.flowbox_backend.Customers.Services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.adapters.out.persistence.Customer;
import com.project.flowbox_backend.Customers.ports.out.CustomerRepository;

@SuppressWarnings("null")
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer_SavesToData(){
        //Arrange: preparar los datos de entrada
        CustomerDTO customerDTO = new CustomerDTO(12345678L, "John", "Doe", 1234567890L);

        //Act: Ejecutar el metodo real del servicio
        customerService.create(customerDTO);

        // Creamos nuestra "red" diseñada específicamente para atrapar objetos Customer
        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);

        // Le decimos al mock que capture lo que le intentaron pasar al método save()
        verify(customerRepository).save(captor.capture());

        // Sacamos a nuestro cliente de la red para inspeccionarlo
        Customer savedCustomer = captor.getValue();

        // ¡Verificamos que el Mapper haya hecho su trabajo a la perfección!
        assertEquals(12345678L, savedCustomer.getDni());
        assertEquals("John", savedCustomer.getNombre());
        assertEquals("Doe", savedCustomer.getApellido());
        assertEquals(1234567890L, savedCustomer.getTelefono());
    }
    //TODO: hacer sad path
}
