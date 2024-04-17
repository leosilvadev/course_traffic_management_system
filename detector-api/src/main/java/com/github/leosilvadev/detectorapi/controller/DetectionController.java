package com.github.leosilvadev.detectorapi.controller;

import com.github.leosilvadev.detectorapi.controller.contracts.DetectionRegistration;
import com.github.leosilvadev.detectorapi.controller.docs.DetectionRegistrar;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/detections")
public class DetectionController implements DetectionRegistrar {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDetection(@RequestBody @Valid DetectionRegistration registration) {
        System.out.println(registration);
    }

}
