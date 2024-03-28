package com.github.leosilvadev.detectorapp.domain;


import java.util.List;
import java.util.UUID;

/**
 *
 * Equipment (location, configuration) 1 - * Lanes 1 - 1 Detector 1 - * Detections
 *
 */

public record Equipment(UUID id, long lat, long lng, List<Lane> lanes) {
}
