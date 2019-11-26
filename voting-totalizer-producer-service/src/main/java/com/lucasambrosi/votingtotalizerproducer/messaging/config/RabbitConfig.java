package com.lucasambrosi.votingtotalizerproducer.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.messaging.totalizer.exchange}")
    private String totalizerExchange;

    @Value("${app.messaging.totalizer.queue}")
    private String totalizerQueue;

    @Value("${app.messaging.routing-key}")
    private String routingKey;

    @Bean
    public Queue totalizerQueue() {
        return QueueBuilder.durable(totalizerQueue).build();
    }

    @Bean
    public TopicExchange totalizerExchange() {
        return ExchangeBuilder.topicExchange(totalizerExchange)
                .durable(true)
                .build();
    }

    @Bean
    public Binding bindTotalizerQueueToExchange() {
        return BindingBuilder.bind(totalizerQueue()).to(totalizerExchange()).with(routingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
