package com.github.vvserdiuk;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.parsing.VkEventParser;
import com.github.vvserdiuk.parsing.VkGroupParser;
import com.github.vvserdiuk.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;
import java.util.List;
import java.util.Set;


@EnableJpaRepositories
@SpringBootApplication
public class EventGatheringApplication extends SpringBootServletInitializer {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EventGatheringApplication.class, args);
	}
}
