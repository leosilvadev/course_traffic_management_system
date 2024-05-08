package com.github.leosilvadev.detectorapi.controller;

import com.github.leosilvadev.detectorapi.controller.contracts.DetectionBatchRegistration;
import com.github.leosilvadev.detectorapi.controller.contracts.DetectionRegistration;
import com.github.leosilvadev.detectorapi.controller.docs.DetectionRegistrar;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class DetectionController implements DetectionRegistrar {

    @PostMapping("/v1/detections")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDetection(@RequestBody @Valid DetectionRegistration registration) {
        System.out.println(registration);
    }

    @PostMapping("/v2/detections")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerDetectionBatch(@RequestBody @Valid DetectionBatchRegistration registration) {
        System.out.println(registration);
    }

}
