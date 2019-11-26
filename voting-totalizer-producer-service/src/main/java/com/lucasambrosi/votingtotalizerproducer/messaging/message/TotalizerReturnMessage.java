package com.lucasambrosi.votingtotalizerproducer.messaging.message;

public class TotalizerReturnMessage {

    private Long sessionId;

    public TotalizerReturnMessage(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }
}
