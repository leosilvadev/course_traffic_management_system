package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Repository
@ConditionalOnProperty(prefix = "equipment", value = "mode", havingValue = "stateless")
public class ExternalDetectionRepository implements DetectionRepository {

    private final RestClient client;

    public ExternalDetectionRepository(final RestClient client) {
        this.client = client;
    }

    @Override
    public Detection register(final UUID equipmentId, final Detection detection) {
        System.out.println("Registering via API V1...");
        final var registration = new DetectionRegistration(detection.id(), equipmentId, detection.speed());
        client.post()
                .uri("/v1/detections")
                .body(registration)
                .retrieve()
                .toBodilessEntity();

        return detection;
    }

    @Override
    public List<Detection> registerMany(final UUID equipmentId, final List<Detection> detections) {
        System.out.println("Registering via API V2...");
        final var registrations = detections.stream()
                .map(detection -> new DetectionRegistration(detection.id(), equipmentId, detection.speed()))
                .toList();

        final var registration = new DetectionBatchRegistration(registrations);

        client.post()
                .uri("/v2/detections")
                .body(registration)
                .retrieve()
                .toBodilessEntity();

        return detections;
    }

    public record DetectionRegistration(UUID id, UUID equipmentId, double speed) implements Serializable {
    }

    public record DetectionBatchRegistration(
            List<DetectionRegistration> detections
    ) implements Serializable {
    }

}

