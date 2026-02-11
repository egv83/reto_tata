package com.tata.service.cuentas.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MovimientoResponseDTO(
        LocalDateTime fecha,
        String cliente,
        String cuenta,
        String tipo,
        BigDecimal saldoInicial,
        Boolean estado,
        BigDecimal movimiento,
        BigDecimal saldoDisponible
) {
}
