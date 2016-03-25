package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Event;

import java.util.List;

/**
 * Created by vvserdiuk on 07.03.2016.
 */
public interface EventService {

    public List<Event> getAll();

    public Event getById(Integer id);
}
