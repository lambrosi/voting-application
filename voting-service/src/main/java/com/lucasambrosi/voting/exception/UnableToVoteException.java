package com.lucasambrosi.voting.exception;

import com.lucasambrosi.voting.enums.UserStatus;

public class UnableToVoteException extends RuntimeException {

    public UnableToVoteException(String cpf, UserStatus userStatus) {
        super("User with CPF '" + cpf + "' is unable to vote because your status is '" + userStatus + "'!");
    }
}
