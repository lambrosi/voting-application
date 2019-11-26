package com.lucasambrosi.voting.exception;

public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
        this("Session not found!");
    }

    public SessionNotFoundException(Long sessionId) {
        super("Session with ID " + sessionId + " not found!");
    }

    public SessionNotFoundException(String message) {
        super(message);
    }
}