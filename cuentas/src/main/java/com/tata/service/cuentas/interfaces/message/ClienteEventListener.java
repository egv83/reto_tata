package com.tata.service.cuentas.interfaces.message;

import com.tata.service.cuentas.domain.entity.ClienteEntity;
import com.tata.service.cuentas.domain.event.ClienteCreadoEvent;
import com.tata.service.cuentas.interfaces.message.config.RabbitConf;
import com.tata.service.cuentas.interfaces.repository.ClienteRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventListener {

    private final ClienteRepository clienteRepository;

    public ClienteEventListener(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @RabbitListener(queues = RabbitConf.QUEUE_CUENTAS)
    public void handleClienteCreado(ClienteCreadoEvent event){
        if(clienteRepository.existsByIdentificacion(event.identificacion())){
            return;
        }

        ClienteEntity clienteEntity = new ClienteEntity(
                event.clienteId(),
                event.identificacion(),
                event.nombre(),
                event.estado()
        );
        clienteRepository.save(clienteEntity);

    }

}
