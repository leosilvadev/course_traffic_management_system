package com.github.leosilvadev.detectorapp;

import com.github.leosilvadev.detectorapp.service.detection.Detector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class DetectorAppApplication {

	public static void main(String[] args) {
		final var context = SpringApplication.run(DetectorAppApplication.class, args);
		final var detectors = (List<Detector>) context.getBean("detectors");
		detectors.forEach(Detector::start);
	}

}
