package com.github.leosilvadev.detectorapi.controller.contracts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DetectionRegistration(
        @NotNull UUID id,
        @NotNull UUID equipmentId,
        @Min(1) double speed) {
}
