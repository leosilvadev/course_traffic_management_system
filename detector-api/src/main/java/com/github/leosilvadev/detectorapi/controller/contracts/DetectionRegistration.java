package com.github.leosilvadev.detectorapi.controller.contracts;

import java.util.UUID;

public record DetectionRegistration(UUID id, UUID equipmentId, double speed) {
}
