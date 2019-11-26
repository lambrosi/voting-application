package com.lucasambrosi.voting.client.response;

import com.lucasambrosi.voting.enums.UserStatus;

public class UserInfoResponse {

    private UserStatus status;

    public UserInfoResponse() {
    }

    public UserInfoResponse(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}