package com.example.team1_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Team1BeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Team1BeApplication.class, args);
	}

}
