package com.tata.service.cuentas.application.mapper;

import com.tata.service.cuentas.application.dto.CuentaResponseDTO;
import com.tata.service.cuentas.domain.entity.ClienteEntity;
import com.tata.service.cuentas.domain.entity.CuentaEntity;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {

    public CuentaResponseDTO toCuentaResponseDTO(CuentaEntity cuentaEntity, ClienteEntity clienteEntity){
        return new CuentaResponseDTO(
                cuentaEntity.getNumeroCuenta(),
                cuentaEntity.getTipoCuenta(),
                cuentaEntity.getSaldoInicial(),
                cuentaEntity.getEstado(), clienteEntity.getNombre()
        );
    }

}
