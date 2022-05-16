package com.acme.zipi.domain.repositories;

import java.util.List;

import com.acme.zipi.domain.model.User;

public interface UsersRepository {

    User create(User user);

    List<User> getAll();

    User getById(String userId);

    void delete(String userId);

    void update(String userId, User user);

}
