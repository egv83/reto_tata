package com.tata.service.clientes.infrastructure.repository;

import com.tata.service.clientes.domain.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
