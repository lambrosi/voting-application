package com.lucasambrosi.voting.api.v1.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information returned after creating a new Topic.")
public class TopicResponse {

    @ApiModelProperty(notes = "The newly created Topic ID.")
    private Long id;

    @ApiModelProperty(notes = "The newly created Topic name.")
    private String name;

    public TopicResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
