package com.lucasambrosi.votingtotalizerproducer.client.response;

import com.lucasambrosi.votingtotalizerproducer.enums.VoteOption;

public class TotalizedValueResponse {

    private VoteOption option;
    private Long amount;

    public TotalizedValueResponse() {
    }

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
