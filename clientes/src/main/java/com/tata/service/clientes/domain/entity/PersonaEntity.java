package com.tata.service.clientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Entity
@Getter
@Table(name = "persona")
//@Inheritance(strategy = InheritanceType.JOINED)
public class PersonaEntity {

    @Id
    @Column(name = "personaid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaid;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 20)
    private String genero;

    private Integer edad;

    @Column(nullable = false, unique = true, length = 30)
    private String identificacion;

    @Column(nullable = false, length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    protected PersonaEntity(){

    }

    public PersonaEntity(String nombre, String genero, Integer edad, String identificacion,
                         String direccion, String telefono) {
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public void actualizarNombre(String nombre){
        if(Objects.nonNull(nombre) && !"".equals(nombre)){
            this.nombre = nombre;
        }
    }

    public void actualizarGenero(String genero){
        if(Objects.nonNull(genero) && !"".equals(genero)){
            this.genero = genero;
        }
    }

    public void actualizarEdad(Integer edad){
        if(Objects.nonNull(edad)) {
            this.edad = edad;
        }
    }

    public void actualizarDireccion(String direccion){
        if(Objects.nonNull(direccion) && !direccion.isBlank()){
            this.direccion = direccion;
        }
    }

    public void actualizarTelefono(String telefono){
        if(Objects.nonNull(telefono) && !telefono.isBlank()){
            this.telefono = telefono;
        }
    }

}
