package com.github.vvserdiuk.parsing;

import com.github.vvserdiuk.model.Event;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by vvserdiuk on 26.02.2016.
 */
//TODO make with junit
public class Test {

    static Logger logger = Logger.getLogger("MyLog");

    public static void main(String[] args) throws IOException {
        // This block configure the logger with handler and formatter
        FileHandler fh = new FileHandler("E:/logs.txt");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        // the following statement is used to log any messages
        logger.info("My first log");

//        testEventParser();
        testGroupParser();
        LocalTime localTime = LocalTime.parse("15:00");

    }

    static public void testEventParser() throws IOException {
        VkEventParser parser = new VkEventParser("http://vk.com/event76516014");
        System.out.println(parser.getTitle());
        System.out.println(parser.getStartDateTime());
        System.out.println(parser.getEndDateTime());
        System.out.println(parser.getPosterUrl());
        System.out.println(parser.getDescription());
    }
    static public void testGroupParser() throws IOException {
        Set<String> eventsToParce = VkGroupParser.getEvents("http://vk.com/a_ryba");
        VkEventParser eventParser;
        Event event;
        for (String e : eventsToParce){
            eventParser = new VkEventParser(e);
            event = eventParser.getEvent();
            logger.info("!!!!!!!!!!!" + e);
            logger.info(event.getTitle());
            logger.info(eventParser.getStartDateTime().toString());
            logger.info(eventParser.getEndDateTime().toString());
            logger.info(eventParser.getPosterUrl());
            logger.info(event.getDescription());
            logger.info("-------------------------------");

        }
    }


}
