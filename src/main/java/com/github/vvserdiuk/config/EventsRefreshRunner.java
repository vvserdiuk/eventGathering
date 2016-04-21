package com.github.vvserdiuk.config;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.parsing.VkEventParser;
import com.github.vvserdiuk.parsing.VkGroupParser;
import com.github.vvserdiuk.repository.CommunityRepository;
import com.github.vvserdiuk.repository.EventRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by vvserdiuk on 21.04.2016.
 */
@Component
public class EventsRefreshRunner implements CommandLineRunner {

    Logger LOG = Logger.getLogger(EventsRefreshRunner.class);

    @Autowired
    EventRepository repository;

    @Autowired
    CommunityRepository communityRepository;


    public void run(String... args) {
        while (true) {
            try {
                LOG.info("bla-bla-bla");
                refresh();
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(){
        try {
            List<Community> communities = communityRepository.getAll();
            LOG.info(communities);

            Set<String> eventsUrlsToParse = new HashSet<>();
            for (Community с : communities) {
                eventsUrlsToParse.addAll(VkGroupParser.getEventsUrls(с.getVkLink()));
            }

            LOG.info(eventsUrlsToParse);
            VkEventParser eventParser;
            Event event;
            for (String e : eventsUrlsToParse){
                eventParser = new VkEventParser(e);
                event = eventParser.getEvent();
                //TODO save with community
                repository.save(event);
            }
        } catch (IOException e) {
            LOG.info(e.getStackTrace());
        }
    }

}