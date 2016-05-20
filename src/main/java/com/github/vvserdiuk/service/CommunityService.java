package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.model.Event;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by vvserdiuk on 07.04.2016.
 */
public interface CommunityService {

    Community getById(Integer id);

    List<Community> findByTitleContaining(String title);

    List<Community> getAll();


}
