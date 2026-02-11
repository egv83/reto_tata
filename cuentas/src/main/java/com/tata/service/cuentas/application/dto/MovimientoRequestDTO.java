package com.tata.service.cuentas.application.dto;

import java.math.BigDecimal;

public record MovimientoRequestDTO(
        String numeroCuenta,
        BigDecimal valor
) {
}
