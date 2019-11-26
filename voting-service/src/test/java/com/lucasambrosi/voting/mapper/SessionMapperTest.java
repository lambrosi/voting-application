package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.SessionRequest;
import com.lucasambrosi.voting.api.v1.response.SessionResponse;
import com.lucasambrosi.voting.dto.SessionDto;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

public class SessionMapperTest {

    @Test
    void sessionRequestToSessionDtoTest() {
        SessionRequest sessionRequest = buildSessionRequest(20L);

        SessionDto dto = SessionMapper.toSessionDto(sessionRequest);

        Assertions.assertEquals(sessionRequest.getName(), dto.getName());
        Assertions.assertEquals(sessionRequest.getTopicId(), dto.getTopicId());
        Assertions.assertEquals(20L, Duration.between(dto.getStartDateTime(), dto.getEndDateTime()).toMinutes());

    }

    @Test
    void sessionRequestToSessionDtoWithDurationNullTest() {
        SessionRequest sessionRequest = buildSessionRequest(null);

        SessionDto dto = SessionMapper.toSessionDto(sessionRequest);

        Assertions.assertEquals(sessionRequest.getName(), dto.getName());
        Assertions.assertEquals(sessionRequest.getTopicId(), dto.getTopicId());
        Assertions.assertNotNull(dto.getStartDateTime());
        Assertions.assertNull(dto.getEndDateTime());
    }

    @Test
    void sessionToSessionResponseTest() {
        Session session = new Session();
        session.setId(1L);

        SessionResponse response = SessionMapper.toSessionResponse(session);
        Assertions.assertEquals(1L, response.getSessionId());
    }

    @Test
    void sessionDtoToSessionTest() {
        SessionDto dto = buildSessionDto();

        Session session = SessionMapper.toSession(dto, new Topic());
        Assertions.assertEquals("Session", session.getName());
    }

    private SessionRequest buildSessionRequest(Long duration) {
        SessionRequest input = new SessionRequest();
        input.setName("Teste!");
        input.setDuration(duration);
        input.setTopicId(7L);
        return input;
    }

    private SessionDto buildSessionDto() {
        SessionDto dto = new SessionDto();
        dto.setName("Session");
        dto.setTopicId(1L);
        dto.setStartDateTime(LocalDateTime.now());
        dto.setEndDateTime(null);
        return dto;
    }
}
