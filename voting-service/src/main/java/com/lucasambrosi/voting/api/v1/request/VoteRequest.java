package com.lucasambrosi.voting.api.v1.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Information needed to making a Vote.")
public class VoteRequest {

    @ApiModelProperty(notes = "The user ID.")
    private Long idUser;

    @ApiModelProperty(notes = "The user CPF.")
    private String cpf;

    @ApiModelProperty(notes = "The vote option. Must be 'SIM' or 'NAO'.")
    private String option;

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

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
