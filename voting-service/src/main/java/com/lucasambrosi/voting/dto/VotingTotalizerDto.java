package com.lucasambrosi.voting.dto;

import com.lucasambrosi.voting.enums.VoteOption;

public class VotingTotalizerDto {

    private Long topicId;
    private String topicName;
    private Long sessionId;
    private VoteOption vote;
    private Long amount;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public VoteOption getVote() {
        return vote;
    }

    public void setVote(VoteOption vote) {
        this.vote = vote;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
