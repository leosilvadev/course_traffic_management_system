package com.github.leosilvadev.detectorapp.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leosilvadev.detectorapp.domain.Detection;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;

import java.io.Serializable;
import java.util.UUID;

@Repository
public class ExternalDetectionRepository implements DetectionRepository {

    private final RestClient client;

    public ExternalDetectionRepository(final RestClient client) {
        this.client = client;
    }

    @Override
    public Detection register(final UUID equipmentId, final Detection detection) {
        client.post()
                .uri("/v1/detections")
                .body(new DetectionRegistration(detection.id(), equipmentId, detection.speed()))
                .retrieve()
                .toBodilessEntity();

        return detection;
    }

    public record DetectionRegistration(UUID id, UUID equipmentId, double speed) implements Serializable {
    }

}

