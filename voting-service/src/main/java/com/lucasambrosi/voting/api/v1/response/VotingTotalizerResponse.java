package com.lucasambrosi.voting.api.v1.response;

import java.util.List;

public class VotingTotalizerResponse {

    private TopicResponse topic;
    private Long sessionId;
    private List<TotalizedValueResponse> votes;

    public TopicResponse getTopic() {
        return topic;
    }

    public void setTopic(TopicResponse topic) {
        this.topic = topic;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public List<TotalizedValueResponse> getVotes() {
        return votes;
    }

    public void setVotes(List<TotalizedValueResponse> votes) {
        this.votes = votes;
    }
}
