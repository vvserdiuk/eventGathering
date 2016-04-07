package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Event;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Created by vvserdiuk on 07.03.2016.
 */
public interface EventService {

    public List<Event> getAll();

    public Event getById(Integer id);

    public List<Event> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    List<Event> getByCommunityId(@Param("communityId") Integer communityId);
}
