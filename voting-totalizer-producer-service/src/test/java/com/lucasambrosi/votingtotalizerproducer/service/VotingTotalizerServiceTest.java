package com.lucasambrosi.votingtotalizerproducer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasambrosi.votingtotalizerproducer.client.VotingServiceClient;
import com.lucasambrosi.votingtotalizerproducer.client.response.TopicResponse;
import com.lucasambrosi.votingtotalizerproducer.client.response.TotalizedValueResponse;
import com.lucasambrosi.votingtotalizerproducer.client.response.VotingTotalizerResponse;
import com.lucasambrosi.votingtotalizerproducer.enums.VoteOption;
import com.lucasambrosi.votingtotalizerproducer.messaging.TotalizerProducer;
import com.lucasambrosi.votingtotalizerproducer.messaging.message.TotalizerReturnMessage;
import com.lucasambrosi.votingtotalizerproducer.messaging.message.VotingTotalizerMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class VotingTotalizerServiceTest {

    @InjectMocks
    private VotingTotalizerService votingTotalizerService;

    @Mock
    private VotingServiceClient votingServiceClient;

    @Mock
    private TotalizerProducer totalizerProducer;

    VotingTotalizerServiceTest() {
        votingTotalizerService = new VotingTotalizerService(votingServiceClient, totalizerProducer, new ObjectMapper());
    }

    @Test
    void totalizerToSendTest() {
        Mockito.when(votingServiceClient.findTotalizersToSend()).thenReturn(this.getTotalizerResponse());

        votingTotalizerService.getTotalizersAndSend();

        ArgumentCaptor<VotingTotalizerMessage> totalizerMessageCaptor = ArgumentCaptor.forClass(VotingTotalizerMessage.class);
        Mockito.verify(totalizerProducer, Mockito.atMost(1)).sendMessageWithVotingTotalizer(totalizerMessageCaptor.capture());

        VotingTotalizerMessage totalizerMessage = totalizerMessageCaptor.getValue();
        Assertions.assertEquals(5L, totalizerMessage.getSessionId());
        Assertions.assertEquals(1L, totalizerMessage.getTopic().getId());
        Assertions.assertEquals("Topic", totalizerMessage.getTopic().getName());
        Assertions.assertEquals(1, totalizerMessage.getVotes().size());

        ArgumentCaptor<TotalizerReturnMessage> returnMessageCaptor = ArgumentCaptor.forClass(TotalizerReturnMessage.class);
        Mockito.verify(totalizerProducer, Mockito.atMost(1)).sendMessageToVotingService(returnMessageCaptor.capture());

        Assertions.assertEquals(5L, returnMessageCaptor.getValue().getSessionId());

    }

    @Test
    void noTotalizerToSendTest() {
        Mockito.when(votingServiceClient.findTotalizersToSend()).thenReturn(Collections.emptyList());

        votingTotalizerService.getTotalizersAndSend();

        Mockito.verify(totalizerProducer, Mockito.never()).sendMessageToVotingService(Mockito.any());
        Mockito.verify(totalizerProducer, Mockito.never()).sendMessageWithVotingTotalizer(Mockito.any());
    }

    private List<VotingTotalizerResponse> getTotalizerResponse() {
        VotingTotalizerResponse totalizerResponse = new VotingTotalizerResponse();
        totalizerResponse.setSessionId(5L);
        totalizerResponse.setTopic(new TopicResponse(1L, "Topic"));
        totalizerResponse.setVotes(Collections.singletonList(new TotalizedValueResponse(VoteOption.SIM, 15L)));
        return Collections.singletonList(totalizerResponse);
    }
}
