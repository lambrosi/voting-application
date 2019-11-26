package com.lucasambrosi.votingtotalizerproducer.messaging.message;

import com.lucasambrosi.votingtotalizerproducer.dto.TopicDto;
import com.lucasambrosi.votingtotalizerproducer.dto.TotalizedValueDto;

import java.util.List;

public class VotingTotalizerMessage {

    private TopicDto topic;
    private Long sessionId;
    private List<TotalizedValueDto> votes;

    public TopicDto getTopic() {
        return topic;
    }

    public void setTopic(TopicDto topic) {
        this.topic = topic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public List<TotalizedValueDto> getVotes() {
        return votes;
    }

    public void setVotes(List<TotalizedValueDto> votes) {
        this.votes = votes;
    }
}
