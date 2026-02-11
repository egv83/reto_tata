package com.tata.service.cuentas.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "cuenta")
public class CuentaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuentaid")
    private Long id; // clave única

    @Column(name = "numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta", nullable = false)
    private String tipoCuenta; // AHORROS | CORRIENTE

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(name = "estado", nullable = false)
    private Boolean estado;

    @Column(name = "cliente_identificacion", nullable = false)
    private String clienteIdentificacion;

    @OneToMany(
            mappedBy = "cuenta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MovimientoEntity> movimientos = new ArrayList<>();

    protected CuentaEntity() {
    }

    public CuentaEntity(String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial,
                        Boolean estado, String clienteIdentificacion) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.clienteIdentificacion = clienteIdentificacion;
    }

    public void agregarMovimiento(MovimientoEntity movimiento) {
        movimientos.add(movimiento);
        movimiento.agregarCuenta(this);
    }

    public boolean isActive() {
        return Boolean.TRUE.equals(estado);
    }

    public void actualizarEstado(boolean estado) {
        this.estado = estado;

    }
}
