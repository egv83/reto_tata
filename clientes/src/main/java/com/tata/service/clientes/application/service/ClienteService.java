package com.tata.service.clientes.application.service;

import com.tata.service.clientes.application.dto.ClienteRequestDTO;
import com.tata.service.clientes.application.dto.ClienteResponseDTO;
import com.tata.service.clientes.application.dto.ClienteUpdateDTO;
import com.tata.service.clientes.domain.entity.ClienteEntity;
import com.tata.service.clientes.domain.entity.PersonaEntity;
import com.tata.service.clientes.domain.exception.ClienteException;
import com.tata.service.clientes.application.mapper.ClienteMapper;
import com.tata.service.clientes.infrastructure.message.ClienteEventPublisher;
import com.tata.service.clientes.infrastructure.repository.ClienteRepository;
import com.tata.service.clientes.infrastructure.repository.PersonaRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.tata.service.clientes.shared.ErrorConstantes.CLIENTE_NO_ENCONTRADO;

@Service
@Transactional
public class ClienteService {

    Logger logger = LoggerFactory.getLogger(ClienteService.class);

    private final ClienteRepository clienteRepository;
    private final PersonaRepository personaRepository;
    private final ClienteMapper clienteMapper;
    private final ClienteEventPublisher clienteEventPublisher;

    public ClienteService(ClienteRepository clienteRepository, PersonaRepository personaRepository,
                          ClienteMapper clienteMapper, ClienteEventPublisher clienteEventPublisher) {
        this.clienteRepository = clienteRepository;
        this.personaRepository = personaRepository;
        this.clienteMapper = clienteMapper;
        this.clienteEventPublisher = clienteEventPublisher;
    }

    public ClienteResponseDTO crearCliente(ClienteRequestDTO request) {
        ClienteEntity clienteEntity = null;

        if (personaRepository.findByIdentificacion(request.identificacion()).isPresent()) {
            throw new ClienteException("Cliente ya existe");
        }

        try {
            String genero = "F".equals(request.genero()) ? "Femenino" : "Masculino";

            PersonaEntity personaEntity = new PersonaEntity(
                    request.nombre(), genero, request.edad(), request.identificacion(),
                    request.direccion(), request.telefono()
            );


            personaRepository.save(personaEntity);

            clienteEntity = new ClienteEntity(
                    request.clave(), personaEntity
            );
            clienteRepository.save(clienteEntity);
        } catch (DataIntegrityViolationException e) {
            throw new ClienteException("Error en crear cliente: " + e);
        }

        try{
            clienteEventPublisher.publicarClienteCreado(clienteEntity);
        }catch (AmqpException e){
            logger.error("Error al publicar el evento", e);
        }

        return clienteMapper.toClienteResponsetDTO(clienteEntity);
    }

    public List<ClienteResponseDTO> getClientes() {
        List<ClienteResponseDTO> listado = new ArrayList<>();
        for (ClienteEntity cliente : clienteRepository.findAll()) {
            listado.add(clienteMapper.toClienteResponsetDTO(cliente));
        }
        return listado;
    }

    public ClienteResponseDTO getClienteId(Long id) {
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(CLIENTE_NO_ENCONTRADO));

        return clienteMapper.toClienteResponsetDTO(cliente);
    }

    public void actualizarPUT(Long id, ClienteUpdateDTO request) {
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(CLIENTE_NO_ENCONTRADO));

        cliente.getPersona().actualizarDireccion(request.direccion());
        cliente.getPersona().actualizarEdad(request.edad());
        cliente.getPersona().actualizarTelefono(request.telefono());
        cliente.actualizarClave(request.clave());

        clienteRepository.save(cliente);

    }

    public void actualizarPATCH(Long id, ClienteRequestDTO request) {
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(CLIENTE_NO_ENCONTRADO));

        if(request.nombre() != null){
            cliente.getPersona().actualizarNombre(request.nombre());
        }

        if(request.genero() != null){
            String genero = "F".equals(request.genero()) ? "Femenino" : "Masculino";
            cliente.getPersona().actualizarGenero(genero);
        }

        if (request.direccion() != null) {
            cliente.getPersona().actualizarDireccion(request.direccion());
        }

        if (request.edad() != null) {
            cliente.getPersona().actualizarEdad(request.edad());
        }

        if (request.telefono() != null) {
            cliente.getPersona().actualizarTelefono(request.telefono());
        }

        if (request.clave() != null) {
            cliente.actualizarClave(request.clave());
        }

        clienteRepository.save(cliente);

    }

    public String eliminar(Long id) {
        ClienteEntity cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException(CLIENTE_NO_ENCONTRADO));

        PersonaEntity persona = personaRepository.findByPersonaid(id).get();

//        cliente.desactivar();
        try {
            clienteRepository.delete(cliente);
        }catch (DataIntegrityViolationException e){
            throw new ClienteException("Error al eliminar el cliente: "+e);
        }

        try {
            personaRepository.delete(persona);
        }catch (DataIntegrityViolationException e){
            throw new ClienteException("Erorr al eliminar la persona: "+e);
        }

//        clienteRepository.save(cliente);
        return "Cliente eliminado";

    }

}
