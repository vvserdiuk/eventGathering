package com.github.vvserdiuk.service;

import com.github.vvserdiuk.model.Community;
import com.github.vvserdiuk.model.Event;
import com.github.vvserdiuk.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Community> findByTitleContaining(String title) {
        return repository.findByTitleContaining(title);
    }

    @Override
    public List<Community> getAll() {
        return repository.findAllSorted();
    }

    @Override
    public Community create(Community community) {
        return repository.save(community);
    }

    @Override
    public void update(Community community) {
        repository.save(community);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }


}
