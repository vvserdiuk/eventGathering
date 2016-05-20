package com.github.vvserdiuk.repository;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.model.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vvserdiuk on 19.03.2016.
 */
@Repository
public interface CommunityRepository extends CrudRepository<Community, Integer> {

    @Query("SELECT c FROM Community c  ORDER BY c.title ASC")
    List<Community> findAllSorted();

    @Query("SELECT c FROM Community c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Community> findByTitleContaining(@Param("title")String title);
}
