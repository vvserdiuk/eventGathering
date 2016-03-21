package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.parsing.VkEventParser;
import com.github.vvserdiuk.parsing.VkGroupParser;
import com.github.vvserdiuk.repository.CommunityRepository;
import com.github.vvserdiuk.repository.EventRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by vvserdiuk on 13.03.2016.
 */
@Service
public class EventsRefreshServiceImpl implements EventsRefreshService{
    Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    EventRepository repository;

    @Autowired
    CommunityRepository communityRepository;

    public boolean refresh(){
        try {
            List<Community> communities = communityRepository.getAll();
            LOG.info(communities);

            Set<String> eventsToParse = new HashSet<>();
            for (Community с : communities) {
                eventsToParse.addAll(VkGroupParser.getEvents(с.getVkLink()));
            }

            LOG.info(eventsToParse);
            VkEventParser eventParser;
            Event event;
            for (String e : eventsToParse){
                eventParser = new VkEventParser(e);
                event = eventParser.getEvent();
                //TODO save with community
                repository.save(event);
            }
            return true;
        } catch (IOException e) {
            LOG.info(e.getStackTrace());
            return false;
        }
    }
}
