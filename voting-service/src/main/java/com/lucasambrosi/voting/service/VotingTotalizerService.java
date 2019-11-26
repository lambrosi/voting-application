package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.api.v1.response.TopicResponse;
import com.lucasambrosi.voting.api.v1.response.TotalizedValueResponse;
import com.lucasambrosi.voting.api.v1.response.VotingTotalizerResponse;
import com.lucasambrosi.voting.dto.VotingTotalizerDto;
import com.lucasambrosi.voting.messaging.message.TotalizerReturnMessage;
import com.lucasambrosi.voting.repository.VotingTotalizerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VotingTotalizerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingTotalizerService.class);

    private VotingTotalizerRepository votingTotalizerRepository;
    private SessionService sessionService;

    public VotingTotalizerService(VotingTotalizerRepository votingTotalizerRepository, SessionService sessionService) {
        this.votingTotalizerRepository = votingTotalizerRepository;
        this.sessionService = sessionService;
    }

    public List<VotingTotalizerResponse> getVotingTotalizerForClosedSessionsAwaitingDelivery() {
        List<VotingTotalizerDto> totalizerDtos = votingTotalizerRepository.getTotalizersForClosedAndNotSentSessions();

        Map<Long, List<VotingTotalizerDto>> totalizerDtoGrouppedByTopicId = totalizerDtos.stream()
                .collect(Collectors.groupingBy(VotingTotalizerDto::getSessionId));

        List<VotingTotalizerResponse> responses = new ArrayList<>();
        for (Map.Entry<Long, List<VotingTotalizerDto>> entry : totalizerDtoGrouppedByTopicId.entrySet()) {
            List<TotalizedValueResponse> votes = this.getTotalizedValue(entry);
            responses.add(this.getVotingTotalizer(entry, votes));
        }
        return responses;
    }

    public void markSessionAsTotalizerSent(TotalizerReturnMessage message) {
        sessionService.markSessionAsTotalizerSent(message.getSessionId());
    }

    private List<TotalizedValueResponse> getTotalizedValue(Map.Entry<Long, List<VotingTotalizerDto>> entry) {
        return entry.getValue()
                .stream()
                .map(it -> new TotalizedValueResponse(it.getVote(), it.getAmount()))
                .collect(Collectors.toList());
    }

    private VotingTotalizerResponse getVotingTotalizer(Map.Entry<Long, List<VotingTotalizerDto>> entry, List<TotalizedValueResponse> votes) {
        VotingTotalizerResponse response = new VotingTotalizerResponse();
        response.setTopic(new TopicResponse(entry.getValue().get(0).getTopicId(), entry.getValue().get(0).getTopicName()));
        response.setSessionId(entry.getKey());
        response.setVotes(votes);
        return response;
    }
}
