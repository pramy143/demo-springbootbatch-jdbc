package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
public class DemoBatchApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DemoBatchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoBatchApplication.class, args);
	}

}