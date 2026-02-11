package com.tata.service.clientes.application.dto;

public record ClienteResponseDTO(
        Long id,
        String nombre,
        String direccion,
        String telefono,
        String identificaion,
        String clave,
        Boolean estado
) {
}
