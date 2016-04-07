package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vvserdiuk on 07.04.2016.
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    CommunityRepository repository;

    @Override
    public Community getById(Integer id) {
        return repository.findOne(id);
    }
}
