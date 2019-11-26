package com.lucasambrosi.voting.client;

import com.lucasambrosi.voting.client.response.UserInfoResponse;
import com.lucasambrosi.voting.enums.UserStatus;
import com.lucasambrosi.voting.exception.InvalidCpfException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

@ExtendWith(SpringExtension.class)
public class UserInfoClientTest {

    @InjectMocks
    private UserInfoClient userInfoClient;

    @Mock
    private UserInfoClient.UserInfoFeignClient userInfoFeignClient;

    @Test
    void getUserInfoByCpfTest() {
        Mockito.when(userInfoClient.getUserInfoByCpf(Mockito.anyString())).thenReturn(new UserInfoResponse(UserStatus.ABLE_TO_VOTE));

        UserInfoResponse response = userInfoClient.getUserInfoByCpf("78954123654");
        Assertions.assertEquals(UserStatus.ABLE_TO_VOTE, response.getStatus());
    }

    @Test
    void getUserInfoByCpfWithExceptionTest() {
        mockRuntimeException();

        Assertions.assertThrows(
                RuntimeException.class,
                () -> userInfoClient.getUserInfoByCpf("AAAAAA")
        );
    }

    @Test
    void getUserInfoByCpfWithInvalidCpfTest() {
        mockFeignExceptionWith404();

        Assertions.assertThrows(
                InvalidCpfException.class,
                () -> userInfoClient.getUserInfoByCpf("415123")
        );
    }

    private void mockRuntimeException() {
        this.mockFeignException(new RuntimeException());
    }

    private void mockFeignExceptionWith404() {
        Response response = Response.builder()
                .status(404)
                .request(Request.create(Request.HttpMethod.GET, "url", Collections.emptyMap(), null))
                .build();
        FeignException feignException = FeignException.errorStatus("", response);

        this.mockFeignException(feignException);
    }

    private void mockFeignException(RuntimeException ex) {
        Mockito.doThrow(ex).when(userInfoFeignClient).getUserInfoByCpf(Mockito.anyString());
    }
}
