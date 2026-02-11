package com.tata.service.cuentas.domain.event;

public record ClienteCreadoEvent(
        Long clienteId,
        String nombre,
        String identificacion,
        Boolean estado
) {
}
