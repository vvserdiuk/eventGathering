package com.github.vvserdiuk.web;

import com.github.vvserdiuk.service.CommunityService;
import com.github.vvserdiuk.service.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by vvserdiuk on 16.04.2016.
 */
@Controller
public class SearchController {

    Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    EventService eventService;

    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getAll(@RequestParam("title") String title, Model model){
        LOG.debug("searching");
        model.addAttribute("events", eventService.findByTitleContaining(title));
        model.addAttribute("communities", communityService.findByTitleContaining(title));
        return "searchResults";
    }

}
