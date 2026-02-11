package com.tata.service.cuentas.interfaces.repository;

import com.tata.service.cuentas.domain.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Long> {
//    Optional<CuentaEntity> findByClienteId(Long clienteId);

    Boolean existsByNumeroCuenta(String numeroCuenta);

    Optional<CuentaEntity> findByNumeroCuenta(String cuenta);
}
