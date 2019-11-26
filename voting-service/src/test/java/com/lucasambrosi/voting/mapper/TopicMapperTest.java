package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.TopicRequest;
import com.lucasambrosi.voting.api.v1.response.TopicResponse;
import com.lucasambrosi.voting.dto.TopicDto;
import com.lucasambrosi.voting.entity.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TopicMapperTest {

    @Test
    void toTopic() {
        TopicDto topicDto = new TopicDto("Name");

        Topic topic = TopicMapper.toTopic(topicDto);
        Assertions.assertEquals(topicDto.getName(), topic.getName());
    }

    @Test
    void topicRequestToTopicDto() {
        TopicRequest request = new TopicRequest();
        request.setName("Topic");

        TopicDto dto = TopicMapper.toTopicDto(request);
        Assertions.assertEquals("Topic", dto.getName());
    }

    @Test
    void topicToTopicResponse() {
        Topic topic = new Topic();
        topic.setId(0L);
        topic.setName("Topic");

        TopicResponse response = TopicMapper.toTopicResponse(topic);
        Assertions.assertEquals(0L, response.getId());
        Assertions.assertEquals("Topic", response.getName());
    }
}
