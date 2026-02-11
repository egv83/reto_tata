package com.tata.service.clientes.application.mapper;

import com.tata.service.clientes.application.dto.ClienteResponseDTO;
import com.tata.service.clientes.domain.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteResponseDTO toClienteResponsetDTO(ClienteEntity clienteEntity){

        return new ClienteResponseDTO(
                clienteEntity.getClienteId(),
                clienteEntity.getPersona().getNombre(),
                clienteEntity.getPersona().getDireccion(),
                clienteEntity.getPersona().getTelefono(),
                clienteEntity.getPersona().getIdentificacion(),
                clienteEntity.getContrasena(),
                clienteEntity.getEstado()
        );

    }

}
