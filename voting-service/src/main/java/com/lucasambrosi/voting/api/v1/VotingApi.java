package com.lucasambrosi.voting.api.v1;

import com.lucasambrosi.voting.api.v1.request.VoteRequest;
import com.lucasambrosi.voting.service.VotingService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/voting")
public class VotingApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingApi.class);

    private VotingService votingService;

    public VotingApi(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("/topic/{topicId}")
    @ApiOperation(value = "Computes a vote for the informed topic.")
    public ResponseEntity create(@PathVariable Long topicId, @RequestBody VoteRequest voteRequest) {
        LOGGER.info("Creating new vote for user {}.", voteRequest.getIdUser());
        votingService.vote(topicId, voteRequest);
        return ResponseEntity.ok().build();
    }
}