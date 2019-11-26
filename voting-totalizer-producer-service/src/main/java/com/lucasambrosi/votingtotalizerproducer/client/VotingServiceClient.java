package com.lucasambrosi.votingtotalizerproducer.client;

import com.lucasambrosi.votingtotalizerproducer.client.response.VotingTotalizerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "${app.url.voting-service}", name = "voting-service")
public interface VotingServiceClient {

    @GetMapping(value = "/v1/totalizer/session/awaiting-delivery")
    List<VotingTotalizerResponse> findTotalizersToSend();
}
