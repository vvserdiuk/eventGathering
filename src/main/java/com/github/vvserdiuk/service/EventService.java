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

    List<Event> getAll();

    Event getById(Integer id);

    List<Event> getFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);

    List<Event> getByCommunityId(@Param("communityId") Integer communityId);

    List<Event> findByTitleContaining(@Param("title")String title);
}
