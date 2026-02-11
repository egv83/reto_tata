package com.tata.service.clientes.domain.event;

public record ClienteCreadoEvent (
        Long clienteId,
        String nombre,
        String identificacion,
        Boolean estado
){
}
