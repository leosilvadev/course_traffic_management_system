package com.github.leosilvadev.detectorapp;

import com.github.leosilvadev.detectorapp.repository.ExternalDetectionRepository;
import com.github.leosilvadev.detectorapp.service.EquipmentStarter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.matchers.MatchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.JsonBody.json;

@SpringBootTest
@ExtendWith(MockServerExtension.class)
class DetectorAppApplicationTests {

	@Autowired
	private EquipmentStarter starter;

	private ClientAndServer clientAndServer;

	public DetectorAppApplicationTests(final ClientAndServer clientAndServer) {
		this.clientAndServer = clientAndServer;
		System.setProperty("api.url", "http://localhost:" + clientAndServer.getPort());
	}

	@Test
	void shouldStartTheAppGenerateSomeDetectionsAndSendThemToTheApi() throws InterruptedException {
		clientAndServer.when(
				request()
						.withMethod("POST")
						.withPath("/v1/detections")
						//.withBody(json(expectedRequest, MatchType.ONLY_MATCHING_FIELDS))
		).respond(
				response().withStatusCode(201)
		);

		starter.start();

		Thread.sleep(5_000);
	}

}
