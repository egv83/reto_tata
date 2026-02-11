package com.tata.service.clientes.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClienteUpdateDTO(
        Integer edad,
        String direccion,
        String telefono,
        String clave
) {

}
