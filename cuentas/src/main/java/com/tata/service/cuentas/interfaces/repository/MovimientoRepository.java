package com.tata.service.cuentas.interfaces.repository;

import com.tata.service.cuentas.domain.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoEntity, Long> {

//    Optional<MovimientoEntity> findByNumeroCuenta(String cuenta);

//    List<MovimientoEntity> findByNumeroCuenta(String cuenta);
//
//    Optional<MovimientoEntity> findTopByCuentaOrderByFechaDesc(String cuenta);

    @Query("SELECT m FROM MovimientoEntity m WHERE m.cuenta.numeroCuenta = :cuenta")
    List<MovimientoEntity> buscarPorNumeroCuenta(@Param("cuenta") String numeroCuenta);

    @Query("SELECT m FROM MovimientoEntity m WHERE m.cuenta.numeroCuenta = :cuenta ORDER BY m.fecha DESC LIMIT 1")
    Optional<MovimientoEntity> buscarUltimoMovimiento(@Param("cuenta") String numeroCuenta);

    List<MovimientoEntity> findByCuentaNumeroCuentaAndFechaBetween(
            String numeroCuenta,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

}
