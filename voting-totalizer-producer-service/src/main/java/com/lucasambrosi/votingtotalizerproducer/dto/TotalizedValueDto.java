package com.lucasambrosi.votingtotalizerproducer.dto;

import com.lucasambrosi.votingtotalizerproducer.enums.VoteOption;

public class TotalizedValueDto {

    private VoteOption option;
    private Long amount;

    public TotalizedValueDto() {
    }

    public TotalizedValueDto(VoteOption option, Long amount) {
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
