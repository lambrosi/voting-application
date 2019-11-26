package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.VoteRequest;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.entity.Vote;
import com.lucasambrosi.voting.enums.VoteOption;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VoteMapperTest {

    @Test
    void voteRequestToVoteTest() {
        VoteRequest voteRequest = buildVoteInput();

        Vote vote = VoteMapper.toVote(voteRequest, new Topic(), new Session());

        Assertions.assertEquals(5475, vote.getIdUser());
        Assertions.assertNotNull(vote.getTopic());
        Assertions.assertNotNull(vote.getSession());
        Assertions.assertEquals(VoteOption.SIM, vote.getVote());
    }

    private VoteRequest buildVoteInput() {
        VoteRequest input = new VoteRequest();
        input.setIdUser(5475L);
        input.setCpf("12345678911");
        input.setOption("SIM");
        return input;
    }
}
