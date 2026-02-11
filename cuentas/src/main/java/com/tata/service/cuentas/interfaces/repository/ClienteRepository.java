package com.tata.service.cuentas.interfaces.repository;

import com.tata.service.cuentas.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByIdentificacion(String identificacion);
    Boolean existsByIdentificacion(String identificacion);
    Optional<ClienteEntity> findByNombre(String nombre);

}
