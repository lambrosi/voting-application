package com.lucasambrosi.votingtotalizerproducer.messaging;

import com.lucasambrosi.votingtotalizerproducer.messaging.message.TotalizerReturnMessage;
import com.lucasambrosi.votingtotalizerproducer.messaging.message.VotingTotalizerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TotalizerProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TotalizerProducer.class);

    private RabbitTemplate rabbitTemplate;
    private String totalizerExchange;
    private String totalizerReturnExchange;
    private String routingKey;

    public TotalizerProducer(RabbitTemplate rabbitTemplate,
                             @Value("${app.messaging.totalizer.exchange}") String totalizerExchange,
                             @Value("${app.messaging.totalizer-return.exchange}") String totalizerReturnExchange,
                             @Value("${app.messaging.routing-key}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.totalizerExchange = totalizerExchange;
        this.totalizerReturnExchange = totalizerReturnExchange;
        this.routingKey = routingKey;
    }

    public void sendMessageWithVotingTotalizer(VotingTotalizerMessage message) {
        LOGGER.info("Sending message with totalizer for Topic {} and Session {}.", message.getTopic().getId(), "1");
        try {
            rabbitTemplate.convertAndSend(totalizerExchange, routingKey, message);
        } catch (Exception ex) {
            LOGGER.error("Error to send message with totalizer for Topic {} and Session {}.", message.getTopic().getId(), "1");
            throw ex;
        }
    }

    public void sendMessageToVotingService(TotalizerReturnMessage totalizerReturnMessage) {
        try {
            rabbitTemplate.convertAndSend(totalizerReturnExchange, routingKey, totalizerReturnMessage);
        } catch (Exception ex) {
            LOGGER.error("Error to send message to voting-service.");
            throw ex;
        }
    }
}
