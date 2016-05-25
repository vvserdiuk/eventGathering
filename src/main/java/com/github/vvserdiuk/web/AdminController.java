package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vvserdiuk on 20.05.2016.
 */
@Controller
public class AdminController {

    @Autowired
    CommunityService communityService;

    @RequestMapping("/admin")
    public String root(Model model){
        model.addAttribute("communities", communityService.getAll());
        return "admin";
    }


    @RequestMapping(value = "/admin/ajax/communities/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String update(@RequestBody Community community, Model model){
        communityService.update(community);
        model.addAttribute("communities", communityService.getAll());
        return "admin :: communityList";
    }

    @RequestMapping(value = "/admin/ajax/communities/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id, Model model){
        communityService.delete(id);
        model.addAttribute("communities", communityService.getAll());
        return "admin :: communityList";
    }
}

