package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.repository.EventRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by vvserdiuk on 07.03.2016.
 */
@Service
public class EventServiseImpl implements EventService{

    Logger LOG = Logger.getLogger(EventServiseImpl.class);
    @Autowired
    EventRepository repository;

    public List<Event> getAll(){
        return repository.getAll();
    }
}
