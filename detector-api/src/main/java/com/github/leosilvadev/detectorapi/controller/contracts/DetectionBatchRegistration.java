package com.github.leosilvadev.detectorapi.controller.contracts;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DetectionBatchRegistration(
        @NotNull List<DetectionRegistration> detections
) {
}
