package com.github.leosilvadev.detectorapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Detector-API",
				description = "Manages the detections made by street equipments",
				contact = @Contact(name = "Leonardo", email = "leosilvadev@gmail.com")
		),
		servers = {
				@Server(url = "http://localhost:8080", description = "local"),
				@Server(url = "https://dev.api.mycompany.com", description = "dev"),
				@Server(url = "https://stg.api.mycompany.com", description = "staging"),
				@Server(url = "https://prd.api.mycompany.com", description = "production"),
		}
)
@SpringBootApplication
public class DetectorApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectorApiApplication.class, args);
	}

}
