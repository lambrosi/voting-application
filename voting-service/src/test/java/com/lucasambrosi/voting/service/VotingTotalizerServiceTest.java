package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.api.v1.response.VotingTotalizerResponse;
import com.lucasambrosi.voting.dto.VotingTotalizerDto;
import com.lucasambrosi.voting.enums.VoteOption;
import com.lucasambrosi.voting.repository.VotingTotalizerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class VotingTotalizerServiceTest {

    @InjectMocks
    private VotingTotalizerService votingTotalizerService;

    @Mock
    private VotingTotalizerRepository votingTotalizerRepository;

    @Mock
    private SessionService sessionService;

    @Test
    void noClosedSessionForSendTest() {
        Mockito.when(votingTotalizerRepository.getTotalizersForClosedAndNotSentSessions()).thenReturn(Collections.emptyList());

        List<VotingTotalizerResponse> totalizerResponses = votingTotalizerService.getVotingTotalizerForClosedSessionsAwaitingDelivery();

        Assertions.assertEquals(0, totalizerResponses.size());
    }

    @Test
    void withClosedSessionForSendTest() {
        Mockito.when(votingTotalizerRepository.getTotalizersForClosedAndNotSentSessions()).thenReturn(buildVotingTotalizerDto());

        List<VotingTotalizerResponse> totalizerResponses = votingTotalizerService.getVotingTotalizerForClosedSessionsAwaitingDelivery();

        VotingTotalizerResponse response = totalizerResponses.get(0);
        Assertions.assertEquals(1L, response.getSessionId());
        Assertions.assertEquals(2L, response.getTopic().getId());
        Assertions.assertEquals(2, response.getVotes().size());
    }

    private List<VotingTotalizerDto> buildVotingTotalizerDto() {
        VotingTotalizerDto dtoSim = new VotingTotalizerDto();
        dtoSim.setAmount(5L);
        dtoSim.setSessionId(1L);
        dtoSim.setTopicId(2L);
        dtoSim.setVote(VoteOption.SIM);

        VotingTotalizerDto dtoNao = new VotingTotalizerDto();
        dtoNao.setAmount(3L);
        dtoNao.setSessionId(1L);
        dtoNao.setTopicId(2L);
        dtoNao.setVote(VoteOption.NAO);

        return Arrays.asList(dtoSim, dtoNao);
    }
}
