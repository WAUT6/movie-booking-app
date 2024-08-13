package com.waut.cinemalocationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CinemaLocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaLocationServiceApplication.class, args);
	}

}
