package com.lucasambrosi.voting.api.v1.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information returned after creating a new Vote.")
public class VoteResponse {

    @ApiModelProperty(notes = "The newly created Vote ID.")
    private Long voteId;

    public VoteResponse(Long voteId) {
        this.voteId = voteId;
    }

    public Long getVoteId() {
        return voteId;
    }
}
