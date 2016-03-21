package com.github.vvserdiuk.web;

import com.github.vvserdiuk.service.EventService;
import com.github.vvserdiuk.service.EventsRefreshService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vvserdiuk on 07.03.2016.
 */

@Controller
@RequestMapping("/mvc")
public class EventController {

    Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    EventService service;

    @Autowired
    EventsRefreshService refreshService;

    @RequestMapping
    public ModelAndView getAll(){
        LOG.debug("getAll MVC");
        ModelAndView mav = new ModelAndView("events");
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        data.add("4");
        data.add("5");
        data.add("6");
        data.add("7");
        data.add("8");

        mav.addObject("datas", data);
        mav.addObject("events", service.getAll());
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
