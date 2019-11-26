package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.dto.SessionDto;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.exception.DuplicateSessionException;
import com.lucasambrosi.voting.exception.SessionNotFoundException;
import com.lucasambrosi.voting.repository.SessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;

    @Mock
    private TopicService topicService;

    @Mock
    private SessionRepository sessionRepository;

    @Test
    void createSessionTest() {
        Mockito.when(topicService.findById(Mockito.anyLong())).thenReturn(buildDefaultTopic());
        Mockito.when(sessionRepository.findByTopicId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        SessionDto sessionDto = buildSessionDto();

        sessionService.create(sessionDto);

        ArgumentCaptor<Session> sessionCaptor = ArgumentCaptor.forClass(Session.class);
        Mockito.verify(sessionRepository, Mockito.atLeast(1)).save(sessionCaptor.capture());

        Session session = sessionCaptor.getValue();
        Assertions.assertEquals(sessionDto.getName(), session.getName());
    }

    @Test
    void createSessionWithAnotherSessionOpenTest() {
        Mockito.when(topicService.findById(Mockito.anyLong())).thenReturn(buildDefaultTopic());

        Session openSession = buildSession(LocalDateTime.now().plusMinutes(10L));
        Mockito.when(sessionRepository.findByTopicId(Mockito.anyLong())).thenReturn(Collections.singletonList(openSession));

        SessionDto sessionDto = buildSessionDto();

        Assertions.assertThrows(
                DuplicateSessionException.class,
                () -> sessionService.create(sessionDto)
        );
    }

    @Test
    void isOpenSessionTest() {
        Session session = buildSession(LocalDateTime.now().plusMinutes(5));
        mockFindByTopicId(session);

        Assertions.assertTrue(sessionService.isOpen(session));
    }

    @Test
    void isNotOpenSessionTest() {
        Session session = buildSession(LocalDateTime.now().minusMinutes(5));
        mockFindByTopicId(session);

        Assertions.assertFalse(sessionService.isOpen(session));
    }

    @Test
    void repositoryFindAllCall() {
        sessionService.findAll();
        Mockito.verify(sessionRepository, Mockito.only()).findAll();
    }

    @Test
    void repositoryFindByIdCall() {
        Mockito.when(sessionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Session()));
        sessionService.findById(5L);

        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(sessionRepository, Mockito.only()).findById(longCaptor.capture());

        Assertions.assertEquals(5L, longCaptor.getValue());
    }

    @Test
    void repositoryFindByIdNotFound() {
        Mockito.when(sessionRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                SessionNotFoundException.class,
                () -> sessionService.findById(1L)
        );
    }

    @Test
    void repositorySaveCall() {
        Session session = buildSession(LocalDateTime.now());

        sessionService.save(session);

        ArgumentCaptor<Session> sessionCaptor = ArgumentCaptor.forClass(Session.class);
        Mockito.verify(sessionRepository, Mockito.only()).save(sessionCaptor.capture());

        Assertions.assertEquals(1L, sessionCaptor.getValue().getId());
        Assertions.assertEquals("Name", sessionCaptor.getValue().getName());
    }

    @Test
    void markSessionAsTotalizerSentTrueTest() {
        Mockito.when(sessionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(buildSession(LocalDateTime.now())));

        sessionService.markSessionAsTotalizerSent(1L);

        ArgumentCaptor<Session> sessionCaptor = ArgumentCaptor.forClass(Session.class);
        Mockito.verify(sessionRepository, Mockito.atMost(1)).save(sessionCaptor.capture());

        Assertions.assertEquals(Boolean.TRUE, sessionCaptor.getValue().getTotalizerSent());
    }

    private Session buildSession(LocalDateTime endDateTime) {
        Session session = new Session();
        session.setId(1L);
        session.setName("Name");
        session.setTopic(new Topic());
        session.setEndDateTime(endDateTime);
        return session;
    }

    private Topic buildDefaultTopic() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("TopicName");
        return topic;
    }

    private SessionDto buildSessionDto() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Name");
        sessionDto.setTopicId(1L);
        return sessionDto;
    }

    private void mockFindByTopicId(Session session) {
        Mockito.when(sessionRepository.findByTopicId(Mockito.anyLong())).thenReturn(Collections.singletonList(session));
    }
}
