package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.api.v1.request.VoteRequest;
import com.lucasambrosi.voting.client.UserInfoClient;
import com.lucasambrosi.voting.client.response.UserInfoResponse;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.enums.UserStatus;
import com.lucasambrosi.voting.exception.InvalidCpfException;
import com.lucasambrosi.voting.exception.NoSessionAvailableException;
import com.lucasambrosi.voting.exception.SessionNotFoundException;
import com.lucasambrosi.voting.exception.TopicNotFoundException;
import com.lucasambrosi.voting.exception.UnableToVoteException;
import com.lucasambrosi.voting.repository.VotingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class VotingServiceTest {

    private static final String VALID_CPF = "10030868025";

    @InjectMocks
    private VotingService votingService;

    @Mock
    private VotingRepository votingRepository;
    @Mock
    private SessionService sessionService;
    @Mock
    private TopicService topicService;
    @Mock
    private UserInfoClient userInfoClient;

    @Test
    void voteWithInvalidCpfTest() {
        VoteRequest voteRequest = buildVoteRequest("756412");

        Assertions.assertThrows(
                InvalidCpfException.class,
                () -> votingService.vote(1L, voteRequest)
        );

        Mockito.verify(votingRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void voteWithUnableToVoteCpfTest() {
        VoteRequest voteRequest = buildVoteRequest(VALID_CPF);

        Mockito.when(userInfoClient.getUserInfoByCpf(Mockito.anyString())).thenReturn(buildUserInfoResponse(UserStatus.UNABLE_TO_VOTE));

        Assertions.assertThrows(
                UnableToVoteException.class,
                () -> votingService.vote(1L, voteRequest)
        );

        Mockito.verify(votingRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void voteWithNoSessionFoundException() {
        Mockito.when(sessionService.findByTopicId(Mockito.anyLong())).thenReturn(null);
        Mockito.when(userInfoClient.getUserInfoByCpf(Mockito.anyString())).thenReturn(buildUserInfoResponse(UserStatus.ABLE_TO_VOTE));

        Assertions.assertThrows(
                SessionNotFoundException.class,
                () -> votingService.vote(1L, buildVoteRequest(VALID_CPF))
        );

        Mockito.verify(votingRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void voteWitNoSessionAvailableException() {
        Mockito.when(sessionService.findByTopicId(Mockito.anyLong())).thenReturn(buildSession());
        Mockito.when(userInfoClient.getUserInfoByCpf(Mockito.anyString())).thenReturn(buildUserInfoResponse(UserStatus.ABLE_TO_VOTE));
        Mockito.when(sessionService.isOpen(Mockito.any())).thenReturn(false);

        Assertions.assertThrows(
                NoSessionAvailableException.class,
                () -> votingService.vote(1L, buildVoteRequest(VALID_CPF))
        );

        Mockito.verify(votingRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void voteWitNoTopicFoundException() {
        Mockito.when(sessionService.findByTopicId(Mockito.anyLong())).thenReturn(buildSession());
        Mockito.when(userInfoClient.getUserInfoByCpf(Mockito.anyString())).thenReturn(buildUserInfoResponse(UserStatus.ABLE_TO_VOTE));
        Mockito.when(sessionService.isOpen(Mockito.any())).thenReturn(true);

        Mockito.doThrow(new TopicNotFoundException(1L)).when(topicService).findById(Mockito.anyLong());

        Assertions.assertThrows(
                TopicNotFoundException.class,
                () -> votingService.vote(1L, buildVoteRequest(VALID_CPF))
        );

        Mockito.verify(votingRepository, Mockito.never()).save(Mockito.any());
    }

    private VoteRequest buildVoteRequest(String cpf) {
        VoteRequest request = new VoteRequest();
        request.setCpf(cpf);
        request.setIdUser(456L);
        request.setOption("SIM");
        return request;
    }

    private Session buildSession() {
        Session session = new Session();
        session.setId(1L);
        session.setTopic(new Topic());
        return session;
    }

    private UserInfoResponse buildUserInfoResponse(UserStatus userStatus) {
        return new UserInfoResponse(userStatus);
    }

}
