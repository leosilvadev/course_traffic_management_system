package com.github.leosilvadev.detectorapi.controller;

import com.github.leosilvadev.detectorapi.controller.contracts.DetectionRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetectionController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDetection(@RequestBody DetectionRegistration registration) {
        System.out.println(registration);
    }


}
