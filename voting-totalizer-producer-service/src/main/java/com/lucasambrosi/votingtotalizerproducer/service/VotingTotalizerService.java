package com.lucasambrosi.votingtotalizerproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasambrosi.votingtotalizerproducer.client.VotingServiceClient;
import com.lucasambrosi.votingtotalizerproducer.client.response.VotingTotalizerResponse;
import com.lucasambrosi.votingtotalizerproducer.messaging.TotalizerProducer;
import com.lucasambrosi.votingtotalizerproducer.messaging.message.TotalizerReturnMessage;
import com.lucasambrosi.votingtotalizerproducer.messaging.message.VotingTotalizerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VotingTotalizerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingTotalizerService.class);

    private VotingServiceClient votingServiceClient;
    private TotalizerProducer totalizerProducer;
    private ObjectMapper objectMapper;

    public VotingTotalizerService(VotingServiceClient votingServiceClient, TotalizerProducer totalizerProducer, ObjectMapper objectMapper) {
        this.votingServiceClient = votingServiceClient;
        this.totalizerProducer = totalizerProducer;
        this.objectMapper = objectMapper;
    }

    public void getTotalizersAndSend() {
        LOGGER.debug("Retrieving totalizers from service 'voting-service'.");
        List<VotingTotalizerResponse> totalizers = votingServiceClient.findTotalizersToSend();

        LOGGER.debug("Sending {} totalizers.", totalizers.size());
        for (VotingTotalizerResponse totalizerResponse : totalizers) {
            totalizerProducer.sendMessageWithVotingTotalizer(objectMapper.convertValue(totalizerResponse, VotingTotalizerMessage.class));
            this.notifyVotingService(totalizerResponse);
        }
    }

    private void notifyVotingService(VotingTotalizerResponse response) {
        LOGGER.info("Notifying voting-service that totalizers for session {} and topic {} have been sent.", response.getSessionId(), response.getTopic().getId());
        totalizerProducer.sendMessageToVotingService(new TotalizerReturnMessage(response.getSessionId()));
    }
}
