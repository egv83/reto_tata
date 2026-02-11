package com.tata.service.clientes.infrastructure.message;

import com.tata.service.clientes.domain.entity.ClienteEntity;
import com.tata.service.clientes.domain.event.ClienteCreadoEvent;
import com.tata.service.clientes.infrastructure.message.config.RabbitConf;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClienteEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ClienteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarClienteCreado(ClienteEntity clienteEntity){
        ClienteCreadoEvent event = new ClienteCreadoEvent(
                clienteEntity.getClienteId(),
                clienteEntity.getPersona().getNombre(),
                clienteEntity.getPersona().getIdentificacion(),
                clienteEntity.getEstado()
        );

        rabbitTemplate.convertAndSend(
                RabbitConf.EXCHANGE_CLIENTES,
                RabbitConf.ROUTING_KEY_CLIENTE_CREADO,
                event
        );
    }

}
