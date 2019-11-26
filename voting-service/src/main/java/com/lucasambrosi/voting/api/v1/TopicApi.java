package com.lucasambrosi.voting.api.v1;

import com.lucasambrosi.voting.api.v1.request.TopicRequest;
import com.lucasambrosi.voting.api.v1.response.TopicResponse;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.mapper.TopicMapper;
import com.lucasambrosi.voting.service.TopicService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/topic")
public class TopicApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(TopicApi.class);

    private TopicService topicService;

    public TopicApi(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    @ApiOperation(value = "Finds all topics.")
    public ResponseEntity<List<TopicResponse>> getAll() {
        LOGGER.info("Getting all topics.");
        List<TopicResponse> responseList = topicService.findAll()
                .stream()
                .map(TopicMapper::toTopicResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{topicId}")
    @ApiOperation(value = "Find a topic by ID informed.")
    public ResponseEntity<TopicResponse> getById(@PathVariable Long topicId) {
        LOGGER.info("Getting topic by id {}.", topicId);
        return ResponseEntity.ok(TopicMapper.toTopicResponse(topicService.findById(topicId)));
    }

    @PostMapping
    @ApiOperation(value = "Create a new topic.")
    public ResponseEntity<TopicResponse> create(@RequestBody TopicRequest topicRequest) {
        LOGGER.info("Creating new topic with name '{}'.", topicRequest.getName());
        Topic topic = topicService.create(TopicMapper.toTopicDto(topicRequest));

        return ResponseEntity.ok(TopicMapper.toTopicResponse(topic));
    }
}
