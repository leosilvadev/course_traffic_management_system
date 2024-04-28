package com.github.leosilvadev.detectorapp;

import com.github.leosilvadev.detectorapp.domain.Detection;
import com.github.leosilvadev.detectorapp.domain.Plate;
import com.github.leosilvadev.detectorapp.repository.ExternalDetectionRepository;
import com.github.leosilvadev.detectorapp.service.EquipmentStarter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.verify.VerificationTimes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import support.Wait;

import java.time.Instant;
import java.util.UUID;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@SpringBootTest(classes = DetectorAppApplicationTests.Config.class)
@ExtendWith(MockServerExtension.class)
class DetectorAppApplicationTests {

	@Autowired
	private EquipmentStarter starter;

	private final UUID equipmentId;

	private final ClientAndServer clientAndServer;

	public DetectorAppApplicationTests(final ClientAndServer clientAndServer) {
		this.equipmentId = UUID.randomUUID();
		this.clientAndServer = clientAndServer;
		System.setProperty("api.url", "http://localhost:" + clientAndServer.getPort());
		System.setProperty("equipment.id", equipmentId.toString());
		System.setProperty("equipment.numberOfLanes", "1");
	}

	@Test
	void shouldStartTheAppGenerateSomeDetectionsAndSendThemToTheApi() {
		final var expectedBody = new ExternalDetectionRepository.DetectionRegistration(
			Config.DEFAULT_DETECTION.id(),
			this.equipmentId,
			Config.DEFAULT_DETECTION.speed()
		);

		final var expectations = clientAndServer.when(
				request()
						.withMethod("POST")
						.withPath("/v1/detections")
						.withBody(json(expectedBody))
		).respond(
				response().withStatusCode(201)
		);

		final var disposables = starter.start();

		Wait.untilAllAreCompleted(disposables);

		clientAndServer.verify(expectations[0].getId(), VerificationTimes.exactly(1));
	}

	@TestConfiguration
	public static class Config {

		public static Detection DEFAULT_DETECTION = new Detection(
				UUID.randomUUID(),
				Plate.generate(),
				100,
				Instant.now()
		);

		@Bean
		public Flux<Detection> detectionGenerator() {
			return Flux.just(DEFAULT_DETECTION);
		}
	}

}
