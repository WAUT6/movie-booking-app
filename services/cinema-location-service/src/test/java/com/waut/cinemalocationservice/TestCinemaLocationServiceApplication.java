package com.waut.cinemalocationservice;

import org.springframework.boot.SpringApplication;

public class TestCinemaLocationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(CinemaLocationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
