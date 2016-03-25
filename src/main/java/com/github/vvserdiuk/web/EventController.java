package com.github.vvserdiuk.web;

import com.github.vvserdiuk.service.EventService;
import com.github.vvserdiuk.service.EventsRefreshService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvserdiuk on 07.03.2016.
 */

@Controller
public class EventController {

    Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    EventService service;

    @Autowired
    EventsRefreshService refreshService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "redirect:events";
    }

    @RequestMapping("/events")
    public ModelAndView getAll(){
        LOG.debug("getAll MVC");
        ModelAndView mav = new ModelAndView("events");
        mav.addObject("events", service.getAll());
        return mav;
    }

    @RequestMapping("/events/{id}")
    public ModelAndView getById(@PathVariable("id") Integer id){
        LOG.debug("get event with id=" + id);
        ModelAndView mav = new ModelAndView("eventPage");
        mav.addObject("event", service.getById(id));
        return mav;
    }

    @RequestMapping("/refresh")
    public String refresh(){
        String result = String.valueOf(refreshService.refresh());
        return result;
    }
    @RequestMapping("/exception")
    public ModelAndView exception(){
        return new ModelAndView("exception");
    }

}
