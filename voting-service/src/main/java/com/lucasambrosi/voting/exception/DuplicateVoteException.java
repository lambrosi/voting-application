package com.lucasambrosi.voting.exception;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException(Long topicId, Long idUser) {
        super("The user with ID " + idUser + " already vote in the topic " + topicId + "!");
    }
}
