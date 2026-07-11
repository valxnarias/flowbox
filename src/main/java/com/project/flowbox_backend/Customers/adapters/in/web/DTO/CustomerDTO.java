package com.project.flowbox_backend.Customers.adapters.in.web.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerDTO {
    @NotBlank(message = "El DNI no puede estar vacio")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe contener exactamente 8 digitos numericos.")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;
    @Pattern(regexp = "^[0-9]{10}$", message = "El telefono debe contener exactamente 10 digitos numericos.")
    private String telefono;

    public CustomerDTO(String dni, String nombre, String apellido, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
