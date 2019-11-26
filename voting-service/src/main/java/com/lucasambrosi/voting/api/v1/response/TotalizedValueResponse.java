package com.lucasambrosi.voting.api.v1.response;

import com.lucasambrosi.voting.enums.VoteOption;

public class TotalizedValueResponse {

    private VoteOption option;
    private Long amount;

    public TotalizedValueResponse(VoteOption option, Long amount) {
        this.option = option;
        this.amount = amount;
    }

    public VoteOption getOption() {
        return option;
    }

    public void setOption(VoteOption option) {
        this.option = option;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
