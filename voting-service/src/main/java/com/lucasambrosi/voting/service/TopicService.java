package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.dto.TopicDto;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.exception.TopicNotFoundException;
import com.lucasambrosi.voting.mapper.TopicMapper;
import com.lucasambrosi.voting.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicService.class);

    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Topic create(final TopicDto topicDto) {
        return this.save(TopicMapper.toTopic(topicDto));
    }

    public List<Topic> findAll() {
        LOGGER.info("Finding all topics.");
        return topicRepository.findAll();
    }

    @Cacheable(cacheNames = "topics", key = "#topicId")
    public Topic findById(final Long topicId) {
        LOGGER.info("Finding topic by ID {}.", topicId);
        return topicRepository.findById(topicId).orElseThrow(() -> new TopicNotFoundException(topicId));
    }

    public Topic save(final Topic topic) {
        LOGGER.info("Saving topic with name '{}'.", topic.getName());
        return topicRepository.save(topic);
    }
}
