package com.github.leosilvadev.detectorapp.domain;

import java.time.Instant;
import java.util.UUID;

public record Detection(UUID id, Plate plate, double speed, Instant time) {
}