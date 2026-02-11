package com.tata.service.clientes.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @NotBlank(message = "Ingrese un nombre")
        String nombre,

        @NotBlank(message = "Ingrese el Genero: Mosculino o Femenino")
        String genero,

        Integer edad,

        @NotBlank(message = "Ingrese la identificación")
        String identificacion,

        @NotBlank(message = "Ingrese la dirección")
        String direccion,

        @NotBlank(message = "Ingrese el telefono")
        String telefono,

        @NotBlank(message = "Ingrese una clave")
        @Size(min = 4, message = "La clave debe tener por lo menos 4 caracteres")
        String clave
) {

}
