package com.tata.service.clientes.infrastructure.message.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class RabbitConf {

    public static final String EXCHANGE_CLIENTES = "clientes.exchange";
    public static final String ROUTING_KEY_CLIENTE_CREADO = "clientes.creado";
    public static final String QUEUE_CUENTAS = "cuentas.queue";

    @Bean
    public TopicExchange clienteExchange(){
        return new TopicExchange(EXCHANGE_CLIENTES);
    }

    @Bean
    public Queue cuentasQueue(){
        return new Queue(QUEUE_CUENTAS,true);
    }

    @Bean
    public Binding bindingCliente(){
        return BindingBuilder
                .bind(cuentasQueue())
                .to(clienteExchange())
                .with(ROUTING_KEY_CLIENTE_CREADO);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

}
