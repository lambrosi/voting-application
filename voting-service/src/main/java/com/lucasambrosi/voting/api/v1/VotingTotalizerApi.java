package com.lucasambrosi.voting.api.v1;

import com.lucasambrosi.voting.api.v1.response.VotingTotalizerResponse;
import com.lucasambrosi.voting.service.VotingTotalizerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/totalizer")
public class VotingTotalizerApi {

    private VotingTotalizerService votingTotalizerService;

    public VotingTotalizerApi(VotingTotalizerService votingTotalizerService) {
        this.votingTotalizerService = votingTotalizerService;
    }

    @GetMapping("/session/awaiting-delivery")
    @ApiOperation(value = "Retrieve a list with all closed sessions.")
    public ResponseEntity<List<VotingTotalizerResponse>> getTotalizerForClosedSessions() {
        return ResponseEntity.ok(votingTotalizerService.getVotingTotalizerForClosedSessionsAwaitingDelivery());
    }
}
