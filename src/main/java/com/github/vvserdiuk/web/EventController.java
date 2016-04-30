package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.DateTimeEntity;
import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.service.EventService;
import com.github.vvserdiuk.util.EventUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
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

    @RequestMapping(value = {"/", "/events"}, method = RequestMethod.GET)
    public String getAll(){
        LOG.debug("getAll MVC");
        return "redirect:/events/pages/1";
    }

    @RequestMapping("/events/pages/{page}")
    public String getAll(@PathVariable("page") int pageNumber, Model model) {

        PageRequest pageRequest = new PageRequest(pageNumber - 1, 8);
        Page<Event> events= service.getByPages(pageRequest);

        model.addAttribute("events", events);

        //Pagination variables
        int current = events.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, events.getTotalPages()); // how many pages to display in the pagination bar

        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
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

    @RequestMapping("/exception")
    public ModelAndView exception(){
        return new ModelAndView("exception");
    }

}
