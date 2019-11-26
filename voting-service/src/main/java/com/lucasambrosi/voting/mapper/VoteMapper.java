package com.lucasambrosi.voting.mapper;

import com.lucasambrosi.voting.api.v1.request.VoteRequest;
import com.lucasambrosi.voting.entity.Session;
import com.lucasambrosi.voting.entity.Topic;
import com.lucasambrosi.voting.entity.Vote;
import com.lucasambrosi.voting.enums.VoteOption;

public class VoteMapper {

    public static Vote toVote(VoteRequest voteRequest, Topic topic, Session session) {
        Vote vote = new Vote();
        vote.setIdUser(voteRequest.getIdUser());
        vote.setTopic(topic);
        vote.setSession(session);
        vote.setVote(VoteOption.of(voteRequest.getOption()));
        return vote;
    }
}
