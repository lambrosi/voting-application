package com.lucasambrosi.voting.api.v1.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information needed to create a Session.")
public class SessionRequest {

    @ApiModelProperty(notes = "The name of the session.")
    private String name;

    @ApiModelProperty(notes = "The topic ID that this session will be associated.")
    private Long topicId;

    @ApiModelProperty(notes = "The duration, in minutes, that this session will remain open. The default value is one minute.")
    private Long duration;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
