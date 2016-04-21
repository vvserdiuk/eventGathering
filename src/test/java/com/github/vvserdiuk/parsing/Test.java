package com.github.vvserdiuk.parsing;

import com.github.vvserdiuk.model.Event;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

/**
 * Created by vvserdiuk on 26.02.2016.
 */
//TODO make with junit
public class Test {

    static Logger LOG = org.apache.log4j.Logger.getLogger(Test.class);

    public static void main(String[] args) throws IOException {
        testEventParser();
//        testGroupParser();

    }

    static public void testEventParser() throws IOException {
        VkEventParser parser = new VkEventParser("http://vk.com/event117780992");
        LOG.info(parser.getTitle());
//        LOG.info(parser.getStartDateTime());
//        LOG.info(parser.getEndDateTime());
//        LOG.info(parser.getPosterUrl());
//        LOG.info(parser.getDescription());
    }
    static public void testGroupParser() throws IOException {
        Set<String> eventsToParce = VkGroupParser.getEventsUrls("http://vk.com/a_ryba");
        VkEventParser eventParser;
        Event event;
        for (String e : eventsToParce){
            eventParser = new VkEventParser(e);
            event = eventParser.getEvent();
            LOG.info("!!!!!!!!!!!" + e);
            LOG.info(event.getTitle());
            LOG.info(eventParser.getStartDateTime().toString());
            LOG.info(eventParser.getEndDateTime().toString());
            LOG.info(eventParser.getPosterUrl());
            LOG.info(event.getDescription());
            LOG.info("-------------------------------");

        }
    }


}
