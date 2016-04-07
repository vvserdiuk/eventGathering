package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.service.CommunityService;
import com.github.vvserdiuk.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by vvserdiuk on 07.04.2016.
 */

@Controller
public class CommunityController {

    @Autowired
    CommunityService service;

    @Autowired
    EventService eventService;

    @RequestMapping("/communities/{id}")
    public String getById(@PathVariable("id") Integer id, Model model){
        List<Event> events = eventService.getByCommunityId(id);
        model.addAttribute("community", service.getById(id));
        model.addAttribute("events", events);

        return "communityPage";
    }
}
