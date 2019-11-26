package com.lucasambrosi.voting.enums;

import com.lucasambrosi.voting.exception.VoteOptionNotAllowedException;

public enum VoteOption {

    SIM,
    NAO;

    public static VoteOption of(String option) {
        if (option.toUpperCase().equals(SIM.toString())) {
            return SIM;
        } else if (option.toUpperCase().equals(NAO.toString())) {
            return NAO;
        }

        throw new VoteOptionNotAllowedException(option);
    }
}
