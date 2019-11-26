package com.lucasambrosi.voting.exception;

public class TopicNotFoundException extends RuntimeException {

    public TopicNotFoundException(Long topicId) {
        super("Topic with ID " + topicId + " not found!");
    }
}
