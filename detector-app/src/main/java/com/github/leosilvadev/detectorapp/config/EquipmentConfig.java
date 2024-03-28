package com.github.leosilvadev.detectorapp.config;

import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import com.github.leosilvadev.detectorapp.service.detection.FakeDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Configuration
@EnableConfigurationProperties(EquipmentConfig.EquipmentProperties.class)
public class EquipmentConfig {

    @Autowired
    private EquipmentProperties properties;

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    @Bean
    public Equipment equipment(final HttpClient client) {
        final var lanes = IntStream.range(0, properties.numberOfLanes())
                .mapToObj(id -> new Lane(id, new FakeDetector(detection -> {
                    final var request = HttpRequest.newBuilder(URI.create("http://localhost:8080"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString("""
                                    {"id": "ff5b0042-96f4-42e8-a79d-8f423e9df348", "equipmentId": "ff5b0042-96f4-42e8-a79d-8f423e9df348", "speed": 90}
                                    """))
                            .build();
                    try {
                        client.send(request, HttpResponse.BodyHandlers.discarding());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }, Executors.newSingleThreadExecutor())))
                .toList();

        return new Equipment(
                properties.id(),
                properties.lat(),
                properties.lng(),
                lanes
        );
    }

    @ConfigurationProperties(prefix = "equipment")
    record EquipmentProperties(UUID id, long lat, long lng, int numberOfLanes) {
    }

}
