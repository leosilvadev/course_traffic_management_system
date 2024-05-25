package com.github.leosilvadev.detectorapp.repository;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.DetectionBatchRegistration;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Repository
public class ExternalDetectionRepository implements DetectionRepository {

    private final RestClient client;

    public ExternalDetectionRepository(final RestClient client) {
        this.client = client;
    }

    @Override
    public List<Detection> registerMany(final UUID equipmentId, final List<Detection> detections) {
        final var registrations = detections.stream()
                .map(detection -> new DetectionBatchRegistration.DetectionRegistration(detection.id(), equipmentId, detection.speed()))
                .toList();

        this.doRegisterMany(new DetectionBatchRegistration(registrations));

        return detections;
    }

    public void doRegisterMany(final DetectionBatchRegistration registration) {
        System.out.println("Registering via API V2...");
        client.post()
                .uri("/v2/detections")
                .body(registration)
                .retrieve()
                .toBodilessEntity();
    }

}

