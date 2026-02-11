package com.tata.service.cuentas.application.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CuentaRequestDTO(
        @NotBlank(message = "Ingrese el tipo de cuenta: AHORRO o CORRIENTE")
        String tipoCuenta,

        @NotBlank(message = "Ingrese el saldo inicial")
        BigDecimal saldoInicial,

        @NotBlank(message = "Ingrese el nombre del cliente")
        String cliente
) {
}
