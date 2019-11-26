package com.lucasambrosi.voting.service;

import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.exception.TopicNotFoundException;
import com.lucasambrosi.voting.repository.TopicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TopicServiceTest {

    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicRepository topicRepository;

    @Test
    void repositoryFindAllCall() {
        topicService.findAll();
        Mockito.verify(topicRepository, Mockito.only()).findAll();
    }

    @Test
    void repositoryFindByIdCall() {
        Mockito.when(topicRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Topic()));
        topicService.findById(5L);

        ArgumentCaptor<Long> longCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(topicRepository, Mockito.only()).findById(longCaptor.capture());

        Assertions.assertEquals(5L, longCaptor.getValue());
    }

    @Test
    void repositoryFindByIdNotFound() {
        Mockito.when(topicRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(
                TopicNotFoundException.class,
                () -> topicService.findById(1L)
        );
    }

    @Test
    void repositorySaveCall() {
        Topic topic = buildTopic();

        topicService.save(topic);

        ArgumentCaptor<Topic> topicCaptor = ArgumentCaptor.forClass(Topic.class);
        Mockito.verify(topicRepository, Mockito.only()).save(topicCaptor.capture());

        Assertions.assertEquals(1L, topicCaptor.getValue().getId());
        Assertions.assertEquals("Name", topicCaptor.getValue().getName());
    }

    private Topic buildTopic() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setName("Name");
        return topic;
    }
}
