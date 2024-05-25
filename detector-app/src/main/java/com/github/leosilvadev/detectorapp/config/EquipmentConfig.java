package com.github.leosilvadev.detectorapp.config;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Equipment;
import com.github.leosilvadev.detectorapp.domain.Lane;
import com.github.leosilvadev.detectorapp.domain.Plate;
import com.github.leosilvadev.detectorapp.repository.DetectionRepository;
import com.github.leosilvadev.detectorapp.service.detection.Detector;
import com.github.leosilvadev.detectorapp.service.detection.FakeDetector;
import com.github.leosilvadev.detectorapp.service.processor.DetectionProcessor;
import com.github.leosilvadev.detectorapp.service.processor.Processor;
import com.github.leosilvadev.detectorapp.support.compression.CompressingClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Configuration
@EnableConfigurationProperties({EquipmentConfig.ApiProperties.class, EquipmentConfig.EquipmentProperties.class})
public class EquipmentConfig {

    @Autowired
    private EquipmentProperties equipmentProperties;

    @Autowired
    private ApiProperties apiProperties;

    @Bean
    public RestClient restClient() {
        return RestClient.builder().baseUrl(apiProperties.url())
                .requestInterceptor(new CompressingClientHttpRequestInterceptor())
                .build();
    }

    @Bean
    public Processor<Detection> detectionProcessor(final DetectionRepository repository) {
        return new DetectionProcessor(equipmentProperties.id(), repository);
    }

    @Bean
    public Flux<Long> detectionTrigger() {
        return Mono.delay(Duration.ofMillis(500)) // 1L
                .repeat();
    }

    @Bean
    public Flux<Detection> detectionGenerator(final Flux<Long> detectionTrigger) {
        final var random = new Random();
        return detectionTrigger.map(__ -> new Detection(
                UUID.randomUUID(),
                Plate.generate(),
                random.nextDouble(50.0, 100.0),
                Instant.now()
        ));
    }

    @Bean
    public Equipment equipment(final Flux<Detection> detectionGenerator, final Processor<Detection> processor) {
        final var lanes = IntStream.range(0, equipmentProperties.numberOfLanes())
                .mapToObj(id -> new Lane(id, new FakeDetector(
                        detectionGenerator, processor, Executors.newSingleThreadExecutor(), new Detector.DetectionBufferSpec(10, Duration.ofMinutes(1))
                )))
                .toList();

        return new Equipment(
                equipmentProperties.id(),
                equipmentProperties.lat(),
                equipmentProperties.lng(),
                lanes
        );
    }

    @ConfigurationProperties(prefix = "equipment")
    record EquipmentProperties(UUID id, long lat, long lng, int numberOfLanes, Mode mode) {
    }

    enum Mode {
        STATEFUL, STATELESS
    }

    @ConfigurationProperties(prefix = "api")
    record ApiProperties(String url) {
    }

}
