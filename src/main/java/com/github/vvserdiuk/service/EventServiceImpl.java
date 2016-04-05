package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.repository.EventRepository;
import com.github.vvserdiuk.util.EventUtil;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vvserdiuk on 07.03.2016.
 */
@Service
public class EventServiceImpl implements EventService{

    Logger LOG = Logger.getLogger(EventServiceImpl.class);
    @Autowired
    EventRepository repository;

    public List<Event> getAll(){
        return Lists.newArrayList(repository.findAll());
    }

    public Event getById(Integer id){
        return repository.findOne(id);
    }

    @Override
    public List<Event> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        List<Event> filtered =  repository.findByStartDateTimeBetween(LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime));
        LOG.info(filtered);

        List<Event> filteredByTimes = filtered.stream().filter(event -> EventUtil.isBetweenTimes(event.getStartDateTime().toLocalTime(), startTime, endTime)).collect(Collectors.toList());
        return filteredByTimes;
    }
}
