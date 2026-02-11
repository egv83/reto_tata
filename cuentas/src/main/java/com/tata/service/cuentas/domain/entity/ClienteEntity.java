package com.tata.service.cuentas.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    private Long id; // mismo id del cliente-ms

    @Column(nullable = false, unique = true, length = 30)
    private String identificacion;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(nullable = false)
    private Boolean estado;

    protected ClienteEntity() {}

    public ClienteEntity(Long id,
                         String identificacion,
                         String nombre,
                         Boolean estado) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.estado = estado;
    }

}
