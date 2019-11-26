package com.lucasambrosi.voting.api.v1;

import com.lucasambrosi.voting.api.v1.request.SessionRequest;
import com.lucasambrosi.voting.api.v1.response.SessionResponse;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.mapper.SessionMapper;
import com.lucasambrosi.voting.service.SessionService;
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
@RequestMapping("/v1/session")
public class SessionApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionApi.class);

    private SessionService sessionService;

    public SessionApi(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    @ApiOperation(value = "Finds all sessions.")
    public ResponseEntity<List<SessionResponse>> getAll() {
        LOGGER.info("Getting all sessions.");
        List<SessionResponse> responseList = sessionService.findAll()
                .stream()
                .map(SessionMapper::toSessionResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Finds a session by ID.")
    public ResponseEntity<SessionResponse> getById(@PathVariable("id") Long id) {
        LOGGER.info("Getting topic by id {}.", id);
        return ResponseEntity.ok(SessionMapper.toSessionResponse(sessionService.findById(id)));
    }

    @PostMapping
    @ApiOperation(
            value = "Creates a session.",
            notes = "For create the session, a valid topicId must be informed."
    )
    public ResponseEntity<SessionResponse> create(@RequestBody SessionRequest sessionRequest) {
        LOGGER.info("Creating new session for topic {} with name '{}'.", sessionRequest.getTopicId(), sessionRequest.getName());
        Session session = sessionService.create(SessionMapper.toSessionDto(sessionRequest));

        return ResponseEntity.ok(SessionMapper.toSessionResponse(session));
    }
}
