package com.github.vvserdiuk.repository;

import com.github.vvserdiuk.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by vvserdiuk on 04.03.2016.
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Integer> {

    Event getByTitle(@Param("title") String title);

//    Collection<Event> findByDateTime(@Param("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime);

    List<Event> findByStartDateTimeBetween(
            @Param("startDateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @Param("endDateTime")   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime);

    List<Event> findByCommunityId(@Param("communityId") Integer communityId);
}
