package com.github.leosilvadev.detectorapp.domain;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record DetectionBatchRegistration(
        List<DetectionRegistration> detections
) implements Serializable {

    public record DetectionRegistration(UUID id, UUID equipmentId, double speed) implements Serializable {
    }
}
