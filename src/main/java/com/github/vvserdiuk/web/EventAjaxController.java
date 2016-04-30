package com.github.vvserdiuk.web;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.service.EventService;
import com.github.vvserdiuk.model.DateTimeEntity;
import com.github.vvserdiuk.util.EventUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by vvserdiuk on 05.04.2016.
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventAjaxController {

    Logger LOG = Logger.getLogger(this.getClass());

    @Autowired
    EventService service;

    @RequestMapping(value = {"/", "/events"}, method = RequestMethod.GET)
    public List<Event> getAll(){
        LOG.debug("getAll MVC");
        return service.getAll();
    }
    @RequestMapping(value = "/events/filter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getFiltered(@RequestBody DateTimeEntity dateTimeEntity
    ){
        LOG.info(dateTimeEntity);
        LocalDate startDate = dateTimeEntity.getStartDate();
        LocalDate endDate   = dateTimeEntity.getEndDate();
        LocalTime startTime = dateTimeEntity.getStartTime();
        LocalTime endTime   = dateTimeEntity.getEndTime();

        return service.getFiltered(startDate != null ? startDate : EventUtil.MIN_DATE, endDate != null ? endDate : EventUtil.MAX_DATE,
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX);
    }
}
