package com.lucasambrosi.voting.dto;

public class TopicDto {

    private String name;

    public TopicDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
