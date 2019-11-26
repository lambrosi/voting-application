package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.SessionRequest;
import com.lucasambrosi.voting.api.v1.response.SessionResponse;
import com.lucasambrosi.voting.dto.SessionDto;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;

import java.time.LocalDateTime;

public class SessionMapper {

    public static SessionResponse toSessionResponse(Session session) {
        return new SessionResponse(session.getId(), session.getName());
    }

    public static Session toSession(SessionDto sessionDto, Topic vinculatedTopic) {
        Session session = new Session();
        session.setName(sessionDto.getName());
        session.setTopic(vinculatedTopic);
        session.setStartDateTime(sessionDto.getStartDateTime());
        session.setEndDateTime(sessionDto.getEndDateTime());
        return session;
    }

    public static SessionDto toSessionDto(SessionRequest sessionRequest) {
        SessionDto dto = new SessionDto();
        dto.setName(sessionRequest.getName());
        dto.setTopicId(sessionRequest.getTopicId());

        if (sessionRequest.getDuration() != null) {
            dto.setEndDateTime(LocalDateTime.now().plusMinutes(sessionRequest.getDuration()));
        }

        return dto;
    }
}
