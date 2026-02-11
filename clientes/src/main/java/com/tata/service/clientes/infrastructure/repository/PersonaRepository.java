package com.tata.service.clientes.infrastructure.repository;

import com.tata.service.clientes.domain.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Long> {

//    boolean existByIdentificacion(String identificacion);

    Optional<PersonaEntity> findByIdentificacion(String identificacion);

    Optional<PersonaEntity> findByPersonaid(Long id);

}
