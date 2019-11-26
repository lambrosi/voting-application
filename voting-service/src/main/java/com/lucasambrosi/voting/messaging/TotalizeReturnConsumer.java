package com.lucasambrosi.voting.messaging;

import com.lucasambrosi.voting.messaging.message.TotalizerReturnMessage;
import com.lucasambrosi.voting.service.VotingTotalizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TotalizeReturnConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TotalizeReturnConsumer.class);

    private VotingTotalizerService votingTotalizerService;

    public TotalizeReturnConsumer(VotingTotalizerService votingTotalizerService) {
        this.votingTotalizerService = votingTotalizerService;
    }

    @RabbitListener(queues = "${app.messaging.totalizer-return.queue}")
    private void consumeTotalizerReturnMessage(TotalizerReturnMessage message) {
        LOGGER.info("Received totalizer return!");
        votingTotalizerService.markSessionAsTotalizerSent(message);
    }
}
