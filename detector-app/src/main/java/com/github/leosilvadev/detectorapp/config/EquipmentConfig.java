package com.github.leosilvadev.detectorapp.config;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import com.github.leosilvadev.detectorapp.repository.DetectionRepository;
import com.github.leosilvadev.detectorapp.service.detection.FakeDetector;
import com.github.leosilvadev.detectorapp.service.processor.DetectionProcessor;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Configuration
@EnableConfigurationProperties(EquipmentConfig.EquipmentProperties.class)
public class EquipmentConfig {

    @Autowired
    private EquipmentProperties properties;

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl("http://localhost:8080").build();
    }

    @Bean
    public Processor<Detection> detectionProcessor(final DetectionRepository repository) {
        return new DetectionProcessor(properties.id(), repository);
    }

    @Bean
    public Equipment equipment(final Processor<Detection> processor) {
        final var lanes = IntStream.range(0, properties.numberOfLanes())
                .mapToObj(id -> new Lane(id, new FakeDetector(processor, Executors.newSingleThreadExecutor())))
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
