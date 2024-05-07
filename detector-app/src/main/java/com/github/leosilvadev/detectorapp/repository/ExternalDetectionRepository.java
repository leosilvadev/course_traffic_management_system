package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.io.Serializable;
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
        System.out.println("Registering via API...");
        final var registration = new DetectionRegistration(detection.id(), equipmentId, detection.speed());
        client.post()
                .uri("/v1/detections")
                .body(registration)
                .retrieve()
                .toBodilessEntity();

        return detection;
    }

    public record DetectionRegistration(UUID id, UUID equipmentId, double speed) implements Serializable {
    }

}

