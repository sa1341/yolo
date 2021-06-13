package com.junyoung.yolo.domain.advertise.api;

import com.junyoung.yolo.domain.advertise.service.DisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/ads")
@RestController
public class DisplayApi {

    private final DisplayService displayService;

    @GetMapping
    public String getAds() {
        return displayService.getAds();
    }
}
