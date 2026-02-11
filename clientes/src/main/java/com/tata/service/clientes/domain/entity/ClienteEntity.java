package com.tata.service.clientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
@Table(name = "cliente")
public class ClienteEntity {

    @Id
    @Column(name = "clienteid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado;

    @OneToOne(optional = false)
    @JoinColumn(name = "personaid", nullable = false, unique = true)
    private PersonaEntity persona;

    protected ClienteEntity() {
    }

    public ClienteEntity( String contrasena, PersonaEntity persona) {
        this.contrasena = contrasena;
        this.estado = true;
        this.persona = persona;
    }

    public boolean isActivo(){
        return Boolean.TRUE.equals(estado);
    }

    public void desactivar(){
        if(!isActivo()) return;
        this.estado = false;
    }

    public void activar(){
        if(isActivo()) return;
        this.estado = true;
    }

    public void actualizarClave(String contrasena){
        if(Objects.nonNull(contrasena) && !contrasena.isBlank()){
            this.contrasena = contrasena;
        }
    }

}
