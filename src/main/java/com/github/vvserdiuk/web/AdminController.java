package com.github.vvserdiuk.web;

import com.github.vvserdiuk.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

