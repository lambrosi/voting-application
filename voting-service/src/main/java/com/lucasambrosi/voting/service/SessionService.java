package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.dto.SessionDto;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.exception.DuplicateSessionException;
import com.lucasambrosi.voting.exception.SessionNotFoundException;
import com.lucasambrosi.voting.mapper.SessionMapper;
import com.lucasambrosi.voting.repository.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class SessionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionService.class);

    private SessionRepository sessionRepository;
    private TopicService topicService;

    public SessionService(SessionRepository sessionRepository, TopicService topicService) {
        this.sessionRepository = sessionRepository;
        this.topicService = topicService;
    }

    public Session create(final SessionDto sessionDto) {
        if (existsSessionWithFutureEndDateTime(sessionDto.getTopicId())) {
            throw new DuplicateSessionException(sessionDto.getTopicId());
        }

        Topic vinculatedTopic = topicService.findById(sessionDto.getTopicId());

        return this.save(SessionMapper.toSession(sessionDto, vinculatedTopic));
    }

    private boolean existsSessionWithFutureEndDateTime(final Long topicId) {
        Session session = this.findByTopicId(topicId);
        return session != null && this.isOpen(session);
    }

    public boolean isOpen(final Session session) {
        LocalDateTime now = LocalDateTime.now();
        return now.isBefore(session.getEndDateTime()) || now.isEqual(session.getEndDateTime());
    }

    public void markSessionAsTotalizerSent(Long sessionId) {
        LOGGER.debug("Saving that the session {} had its totalizers sent", sessionId);
        Session session = this.findById(sessionId);
        session.setTotalizerSent(Boolean.TRUE);

        this.save(session);
    }

    public List<Session> findAll() {
        LOGGER.info("Finding all sessions.");
        return sessionRepository.findAll();
    }

    @Cacheable(value = "sessions", key = "#sessionId")
    public Session findById(final Long sessionId) {
        LOGGER.info("Finding session by ID {}.", sessionId);
        return sessionRepository.findById(sessionId).orElseThrow(() -> new SessionNotFoundException(sessionId));
    }

    @Cacheable(value = "sessions", key = "#sessionId")
    public Session findByTopicId(final Long sessionId) {
        LOGGER.info("Finding session by Topic ID {}.", sessionId);
        return sessionRepository.findByTopicId(sessionId)
                .stream()
                .max(Comparator.comparing(Session::getEndDateTime))
                .orElse(null);
    }

    public Session save(final Session session) {
        LOGGER.info("Saving session with name '{}' for topic with ID {}.", session.getName(), session.getTopic().getId());
        return sessionRepository.save(session);
    }
}
