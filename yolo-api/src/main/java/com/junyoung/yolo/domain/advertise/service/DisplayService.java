package com.junyoung.yolo.domain.advertise.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class DisplayService {

    private WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081").build();

    @HystrixCommand(fallbackMethod = "getAdsFallback",
    commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    public String getAds() {
        return webClient.get()
                .uri("/api/v1/boards/hystrix")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String getAdsFallback() {
        return "기본광고";
    }
}
