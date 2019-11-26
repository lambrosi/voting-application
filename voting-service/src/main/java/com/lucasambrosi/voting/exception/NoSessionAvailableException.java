package com.lucasambrosi.voting.exception;

public class NoSessionAvailableException extends RuntimeException {

    public NoSessionAvailableException(Long topicId) {
        super("No open session for topic with ID " + topicId);
    }
}
