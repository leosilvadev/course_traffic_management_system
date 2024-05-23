package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.DetectionBatchRegistration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

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
    public List<Detection> registerMany(final UUID equipmentId, final List<Detection> detections) {
        System.out.println("Registering via API V2...");
        final var registrations = detections.stream()
                .map(detection -> new DetectionBatchRegistration.DetectionRegistration(detection.id(), equipmentId, detection.speed()))
                .toList();

        final var registration = new DetectionBatchRegistration(registrations);

        client.post()
                .uri("/v2/detections")
                .body(registration)
                .retrieve()
                .toBodilessEntity();

        return detections;
    }

}

