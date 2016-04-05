package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.service.EventService;
import com.github.vvserdiuk.service.EventsRefreshService;
import com.github.vvserdiuk.model.DateTimeEntity;
import com.github.vvserdiuk.util.EventUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @RequestMapping(value = {"/", "/events"}, method = RequestMethod.GET)
    public String getAll(Model model){
        LOG.debug("getAll MVC");
        model.addAttribute("events", service.getAll());
        return "events";
    }

    @RequestMapping("/events/{id}")
    public String getById(@PathVariable("id") Integer id, Model model){
        LOG.info("get event with id=" + id);
        model.addAttribute("event", service.getById(id));
        return "eventPage";
    }

    @RequestMapping(value = "/events/filter", method = RequestMethod.POST)
    public String getFiltered(DateTimeEntity dateTimeEntity, Model model){
        LOG.info(dateTimeEntity);
        LocalDate startDate = dateTimeEntity.getStartDate();
        LocalDate endDate   = dateTimeEntity.getEndDate();
        LocalTime startTime = dateTimeEntity.getStartTime();
        LocalTime endTime   = dateTimeEntity.getEndTime();

        List<Event> filtered = service.getFiltered(startDate != null ? startDate : EventUtil.MIN_DATE, endDate != null ? endDate : EventUtil.MAX_DATE,
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX);

        model.addAttribute("events", filtered);
        return "events";
    }

    //TODO change by trigger
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
