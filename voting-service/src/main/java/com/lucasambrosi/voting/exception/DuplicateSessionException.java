package com.lucasambrosi.voting.exception;

public class DuplicateSessionException extends RuntimeException {

    public DuplicateSessionException(Long topicId) {
        super("There is already an open session for the topic " + topicId + "!");
    }
}
