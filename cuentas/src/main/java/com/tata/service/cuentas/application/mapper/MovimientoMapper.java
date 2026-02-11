package com.tata.service.cuentas.application.mapper;

import com.tata.service.cuentas.application.dto.MovimientoResponseDTO;
import com.tata.service.cuentas.domain.entity.MovimientoEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class MovimientoMapper {

    public MovimientoResponseDTO toMovimientoDTO(MovimientoEntity movimiento){
        return new MovimientoResponseDTO(
                movimiento.getFecha(),
                "CLIENTE",
                movimiento.getCuenta().getNumeroCuenta(),
                movimiento.getCuenta().getTipoCuenta(),
                movimiento.getCuenta().getSaldoInicial(),
                movimiento.getCuenta().getEstado(),
                movimiento.getValor(),
                movimiento.getSaldo()
        );
    }

}