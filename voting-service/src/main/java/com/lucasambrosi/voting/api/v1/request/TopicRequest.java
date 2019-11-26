package com.lucasambrosi.voting.api.v1.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information needed to create a Topic.")
public class TopicRequest {

    @ApiModelProperty(notes = "The name of the Topic.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
