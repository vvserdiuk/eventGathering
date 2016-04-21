package com.github.vvserdiuk;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.parsing.VkEventParser;
import com.github.vvserdiuk.parsing.VkGroupParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventGatheringApplication.class)
@WebAppConfiguration
public class EventGatheringApplicationTests {

	@Test
	public void contextLoads() throws IOException {
		Set<String> eventsToParse = VkGroupParser.getEventsUrls("http://vk.com/a_ryba");
		VkEventParser eventParser;
		Event event;
		for (String e : eventsToParse){
			eventParser = new VkEventParser(e);
//            event = eventParser.getEvent();

//            System.out.println(eventParser.getStartDateTime());
//            System.out.println(eventParser.getEndDateTime());
			System.out.println(eventParser.getTitle());
			System.out.println(eventParser.getPosterUrl());
			System.out.println(eventParser.getDescription());

		}
	}
}



