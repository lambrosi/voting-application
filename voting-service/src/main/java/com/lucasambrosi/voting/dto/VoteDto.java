package com.lucasambrosi.voting.dto;

import com.lucasambrosi.voting.enums.VoteOption;

public class VoteDto {

    private Long idUser;
    private String cpf;
    private VoteOption option;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public VoteOption getOption() {
        return option;
    }

    public void setOption(VoteOption option) {
        this.option = option;
    }
}
