package com.tata.service.cuentas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "movimiento")
public class MovimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimientoid")
    private Long id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "tipo_movimiento",nullable = false)
    private String tipoMovimiento; // DEBITO | CREDITO

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "saldo", nullable = false)
    private BigDecimal saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "numero_cuenta", referencedColumnName = "numero_cuenta",nullable = false)
    private CuentaEntity cuenta;

    protected MovimientoEntity() {}

    public MovimientoEntity(LocalDateTime fecha, String tipoMovimiento,
                            BigDecimal valor, BigDecimal saldo) {
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
    }

    public void agregarCuenta(CuentaEntity cuenta){
        this.cuenta = cuenta;
    }
}
