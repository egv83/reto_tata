package com.tata.service.cuentas.application.dto;

import java.math.BigDecimal;

public record CuentaResponseDTO(
        String numeroCuenta,
        String tipoCuenta,
        BigDecimal saldoInicial,
        Boolean estado,
        String Cliente
) {
}
