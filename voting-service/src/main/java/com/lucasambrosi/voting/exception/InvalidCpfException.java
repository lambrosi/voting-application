package com.lucasambrosi.voting.exception;

public class InvalidCpfException extends RuntimeException {

    public InvalidCpfException(String cpf) {
        super("The CPF '" + cpf + "' informed is invalid!");
    }
}
