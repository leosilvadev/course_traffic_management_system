package com.github.leosilvadev.detectorapp.domain;

import java.time.Instant;
import java.util.UUID;

public record Detection(UUID id, String plate, long speed, Instant time) {
}


/**
 * Equipment
 *  - Lane(s)
 *      - Detector
 *          - Detection
 */