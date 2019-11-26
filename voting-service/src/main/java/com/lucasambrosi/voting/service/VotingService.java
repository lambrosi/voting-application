package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.api.v1.request.VoteRequest;
import com.lucasambrosi.voting.client.UserInfoClient;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.entity.Vote;
import com.lucasambrosi.voting.enums.UserStatus;
import com.lucasambrosi.voting.exception.DuplicateVoteException;
import com.lucasambrosi.voting.exception.InvalidCpfException;
import com.lucasambrosi.voting.exception.NoSessionAvailableException;
import com.lucasambrosi.voting.exception.SessionNotFoundException;
import com.lucasambrosi.voting.exception.UnableToVoteException;
import com.lucasambrosi.voting.mapper.VoteMapper;
import com.lucasambrosi.voting.repository.VotingRepository;
import com.lucasambrosi.voting.util.CpfValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class VotingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingService.class);

    private VotingRepository votingRepository;
    private TopicService topicService;
    private SessionService sessionService;
    private UserInfoClient userInfoClient;

    public VotingService(TopicService topicService, SessionService sessionService, VotingRepository votingRepository, UserInfoClient userInfoClient) {
        this.topicService = topicService;
        this.sessionService = sessionService;
        this.votingRepository = votingRepository;
        this.userInfoClient = userInfoClient;
    }

    public void vote(final Long topicId, final VoteRequest voteRequest) {
        LOGGER.info("Voting for topic ID {}. Voting option: '{}'.", topicId, voteRequest.getOption());

        this.validateUserCpf(voteRequest.getCpf());

        Session relatedSession = sessionService.findByTopicId(topicId);
        this.validateSession(relatedSession);

        Topic relatedTopic = topicService.findById(topicId);

        try {
            this.save(VoteMapper.toVote(voteRequest, relatedTopic, relatedSession));
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateVoteException(topicId, voteRequest.getIdUser());
        }
    }

    private void validateUserCpf(final String cpf) {
        this.validateCpf(cpf);

        UserStatus userStatus = userInfoClient.getUserInfoByCpf(cpf).getStatus();
        if (!userIsAbleToVote(userStatus)) {
            throw new UnableToVoteException(cpf, userStatus);
        }
    }

    private void validateSession(final Session session) {
        if (session == null) {
            throw new SessionNotFoundException();
        }

        if (!sessionService.isOpen(session)) {
            throw new NoSessionAvailableException(session.getTopic().getId());
        }
    }

    private void validateCpf(final String cpf) {
        if (!CpfValidator.isValid(cpf)) {
            throw new InvalidCpfException(cpf);
        }
    }

    private boolean userIsAbleToVote(final UserStatus userStatus) {
        return UserStatus.ABLE_TO_VOTE == userStatus;
    }

    public Vote save(final Vote vote) {
        LOGGER.info("Saving vote with option '{}' for topic with ID {}.", vote.getVote(), vote.getTopic().getId());
        return votingRepository.save(vote);
    }
}
