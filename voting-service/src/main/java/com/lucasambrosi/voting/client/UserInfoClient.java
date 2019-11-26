package com.lucasambrosi.voting.client;

import com.lucasambrosi.voting.client.response.UserInfoResponse;
import com.lucasambrosi.voting.exception.InvalidCpfException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UserInfoClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoClient.class);

    private UserInfoFeignClient userInfoFeignClient;

    public UserInfoClient(UserInfoFeignClient userInfoFeignClient) {
        this.userInfoFeignClient = userInfoFeignClient;
    }

    public UserInfoResponse getUserInfoByCpf(String cpf) {
        try {
            return userInfoFeignClient.getUserInfoByCpf(cpf);
        } catch (FeignException ex) {
            if (HttpStatus.NOT_FOUND.value() == ex.status()) {
                throw new InvalidCpfException(cpf);
            }

            LOGGER.error("Error in consult user information.", ex);
            throw new RuntimeException(ex);
        }
    }

    @FeignClient(url = "${app.url.user-info-service}", name = "user-info")
    interface UserInfoFeignClient {
        @GetMapping(value = "/users/{cpf}")
        UserInfoResponse getUserInfoByCpf(@PathVariable("cpf") String cpf);
    }

}