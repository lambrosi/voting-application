package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.TopicRequest;
import com.lucasambrosi.voting.api.v1.response.TopicResponse;
import com.lucasambrosi.voting.dto.TopicDto;
import com.lucasambrosi.voting.entity.Topic;

public class TopicMapper {

    public static Topic toTopic(TopicDto topicDto) {
        Topic topic = new Topic();
        topic.setName(topicDto.getName());
        return topic;
    }

    public static TopicDto toTopicDto(TopicRequest topicRequest) {
        return new TopicDto(topicRequest.getName());
    }

    public static TopicResponse toTopicResponse(Topic topic) {
        return new TopicResponse(topic.getId(), topic.getName());
    }
}
