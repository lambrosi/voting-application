package com.lucasambrosi.voting.api.v1.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information returned after creating a new Session.")
public class SessionResponse {

    @ApiModelProperty(notes = "The Session ID.")
    private Long sessionId;

    @ApiModelProperty(notes = "The Session name.")
    private String name;

    public SessionResponse(Long sessionId, String name) {
        this.sessionId = sessionId;
        this.name = name;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }
}
