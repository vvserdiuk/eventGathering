package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vvserdiuk on 24.05.2016.
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityAjaxController {

    @Autowired
    CommunityService communityService;

    @RequestMapping(value = "/communities/{id}", method = RequestMethod.GET)
    public Community get(@PathVariable("id") Integer id){
        return communityService.getById(id);
    }
}
