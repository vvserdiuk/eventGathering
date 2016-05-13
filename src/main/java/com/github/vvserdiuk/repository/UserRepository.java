package com.github.vvserdiuk.repository;

import com.github.vvserdiuk.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vvserdiuk on 13.05.2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
