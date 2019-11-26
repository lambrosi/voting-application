package com.lucasambrosi.voting.exception;

public class VoteOptionNotAllowedException extends RuntimeException {

    public VoteOptionNotAllowedException(String voteOption) {
        super("Vote option '" + voteOption + "' not allowed.");
    }
}
