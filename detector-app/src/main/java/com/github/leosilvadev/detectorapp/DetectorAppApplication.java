package com.github.leosilvadev.detectorapp;

import com.github.leosilvadev.detectorapp.service.EquipmentStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(DetectorRuntimeHints.class)
public class DetectorAppApplication {

	public static void main(String[] args) {
		final var context = SpringApplication.run(DetectorAppApplication.class, args);
		final var starter = context.getBean(EquipmentStarter.class);
		starter.start();
	}

}
