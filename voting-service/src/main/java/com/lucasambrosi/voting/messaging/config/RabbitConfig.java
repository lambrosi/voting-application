package com.lucasambrosi.voting.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
public class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${app.messaging.totalizer-return.exchange}")
    private String totalizerReturnExchange;
    @Value("${app.messaging.totalizer-return.queue}")
    private String totalizerReturnQueue;
    @Value("${app.messaging.totalizer-return.routing-key}")
    private String totalizerReturnRoutingKey;

    @Bean
    public Queue totalizerReturnQueue() {
        return QueueBuilder.durable(totalizerReturnQueue).build();
    }

    @Bean
    public TopicExchange totalizerReturnExchange() {
        return ExchangeBuilder.topicExchange(totalizerReturnExchange).durable(true).build();
    }

    @Bean
    public Binding bindingTotalizerQueueToExchange() {
        return BindingBuilder.bind(totalizerReturnQueue()).to(totalizerReturnExchange()).with(totalizerReturnRoutingKey);
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        MappingJackson2MessageConverter mappingJackson2MessageConverter = new MappingJackson2MessageConverter();

        ObjectMapper objectMapper = mappingJackson2MessageConverter.getObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return mappingJackson2MessageConverter;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}
