package com.project.flowbox_backend.Customers.adapters.in.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.flowbox_backend.Customers.Services.CustomerService;
import com.project.flowbox_backend.Customers.adapters.in.web.DTO.CustomerDTO;
import com.project.flowbox_backend.Customers.domain.exceptions.CustomerAlreadyExistsException;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CustomerService customerService;

        private CustomerDTO createCustomerDTO() {
                return new CustomerDTO("12345678", "Juan", "Perez", "1234567890");
        }

        // ======================================== TESTS para path:
        // /api/v1/customers========================================
        @SuppressWarnings("null")
        @Test
        public void testCreateCustomer_Returns201() throws Exception {
                // Arrange: Preparamos los datos del DTO
                CustomerDTO newCustomer = createCustomerDTO();

                // Transformamos el objeto a un String en formato JSON
                String jsonBody = objectMapper.writeValueAsString(newCustomer);

                // Act & Assert: Simulamos el POST y verificamos el status 201
                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isCreated());

                // Refactor: NUEVO ASSERT: Verificamos la interacción interna
                verify(customerService).create(any(CustomerDTO.class));
        }

        @SuppressWarnings("null")
        @ParameterizedTest
        @CsvSource({
                        " , Valentin, Arias",
                        "48732633, ,Arias",
                        "48734400, Valentin, "
        })
        public void testCreateCustomer_NullOrEmptyFields_return400(String dni, String nombre, String apellido)
                        throws Exception {
                // Arrange: Preparar los datos del DTO
                CustomerDTO invalidCustomer = new CustomerDTO(dni, nombre, apellido, apellido);

                // Transformar el objeto a un String en formato json
                String jsonBody = objectMapper.writeValueAsString(invalidCustomer);

                // Act & Assert: Simular el POSt con datos inválidos y verificar el status 400
                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isBadRequest());

        }

        @SuppressWarnings("null")
        @ParameterizedTest
        @CsvSource({
                        " 123456789, John, Doe, 2994636441", // Dni demasiado largo
                        "1234567, John, Doe, 2994636441", // Dni demasiado corto
                        "a2345678, John, Doe, 2994636441", // Dni con letras
                        "-12345678, jhon, Doe, 2994636441", // Dni negativo
                        "12345678, John, Doe, -2994636441", // Telefono negativo
                        "12345678, John, Doe, 299463644a", // Telefono con letras
                        "12345678, John, Doe, 29946364412222222", // Telefono demasiado largo
                        "12345678, John, Doe, 299463644", // Telefono demasiado corto
        })
        public void testCreateCustomer_InvalidData_return400(String dni, String nombre, String apellido,
                        String telefono)
                        throws Exception {
                // Arrange: Preparar los datos del DTO
                CustomerDTO invalidCustomer = new CustomerDTO(dni, nombre, apellido, telefono);

                // Transformar el objeto a un String en formato json
                String jsonBody = objectMapper.writeValueAsString(invalidCustomer);

                // Act & Assert: Simular el POSt con datos inválidos y verificar el status 400
                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isBadRequest());
        }

        @SuppressWarnings("null")
        @Test
        public void testCreateCustomer_CustomerAlreadyExists_return409() throws Exception {
                // Arrange: Preparamos los datos del DTO
                CustomerDTO newCustomer = new CustomerDTO("50734782", "Juan", "Pérez", "2994636441");

                // Transformamos el objeto a un String en formato JSON
                String jsonBody = objectMapper.writeValueAsString(newCustomer);

                // Simulamos que al llamar al servicio lanza un error
                // (CustomerAlreadyExistsException)
                doThrow(new CustomerAlreadyExistsException("El cliente ya existe")).when(customerService)
                                .create(any(CustomerDTO.class));

                // Act & Assert: Simulamos el POST y verificamos el status 409
                mockMvc.perform(post("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonBody))
                                .andExpect(status().isConflict());
        }
        // ======================================== END TESTS para path:
        // /api/v1/customers========================================

        // ======================================== TESTS para path: GET
        // /api/v1/customers========================================

        @SuppressWarnings("null")
        @Test
        public void testGetAllCustomers_Returns200() throws Exception {
                // Arrange: Preparamos los datos del DTO
                CustomerDTO newCustomer = createCustomerDTO();
                List<CustomerDTO> customerList = List.of(newCustomer);

                // Mockeamos el servicio para que cuando el controlador llame a buscar a todos,
                // devuelva una lista
                when(customerService.findAll()).thenReturn(customerList);

                // Act & Assert: Simulamos el GET y verificamos el status 200
                mockMvc.perform(get("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()) // 1. Verifica status 200 OK
                                .andExpect(jsonPath("$").isArray()) // 2. Verifica que la respuesta sea un arreglo JSON
                                .andExpect(jsonPath("$.length()").value(1)) // 3. Verifica que tenga 1 elemento
                                .andExpect(jsonPath("$[0].dni").value("12345678")) // 4. Verifica los campos del primer
                                                                                   // elemento
                                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                                .andExpect(jsonPath("$[0].apellido").value("Perez"));

                // Refactor: verificar que llame al metodo del servicio
                verify(customerService).findAll();
        }

        @SuppressWarnings("null")
        @Test
        public void testGetAllCustomers_ServiceThrowsException_Returns500() throws Exception {
                // Arrange: Simulamos que el servicio falla con un error de la base de datos
                when(customerService.findAll()).thenThrow(new RuntimeException("DataBase connection failed"));

                // Act & Assert: Llamamos al endpoint GET y esperamos un error 500
                mockMvc.perform(get("/api/v1/customers")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isInternalServerError()); // 500

                // Verificamos que el método del servicio fue llamado
                verify(customerService).findAll();
        }
        // ======================================== END TESTS para path GET
        // /api/v1/customers========================================

        // ======================================== TESTS para path GET
        // /api/v1/customers/{id}========================================

}
