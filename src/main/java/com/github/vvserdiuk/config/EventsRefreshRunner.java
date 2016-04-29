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
                refresh();
                TimeUnit.MINUTES.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void refresh(){
        try {
            List<Community> communities = communityRepository.getAll();
            LOG.info(communities);

            Set<EventUrlWithCommunity> eventUrlsWithCommunities = new HashSet<>();
            for (Community community : communities) {
                Set<String> eventUrls = VkGroupParser.getEventsUrls(community.getVkLink());
                eventUrls.forEach(url -> eventUrlsWithCommunities.add(new EventUrlWithCommunity(url, community)));
            }

            VkEventParser eventParser;
            Event event;
            for (EventUrlWithCommunity urlWithCommunity : eventUrlsWithCommunities){
                eventParser = new VkEventParser(urlWithCommunity.eventUrl);
                event = eventParser.getEvent();
                event.setCommunity(urlWithCommunity.community);
                repository.save(event);
            }
        } catch (IOException e) {
            LOG.info(e.getStackTrace());
        }
    }

    private class EventUrlWithCommunity {
        String eventUrl;
        Community community;

        EventUrlWithCommunity(String eventUrl, Community community) {
            this.eventUrl = eventUrl;
            this.community = community;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            EventUrlWithCommunity that = (EventUrlWithCommunity) o;

            return eventUrl.equals(that.eventUrl);

        }
        @Override
        public int hashCode() {
            return eventUrl.hashCode();
        }
    }

}