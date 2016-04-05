package com.github.vvserdiuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;


@EnableJpaRepositories
@SpringBootApplication
public class EventGatheringApplication{

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EventGatheringApplication.class, args);
	}
}
