package com.acme.zipi.infra.persistence.jpa.adapters;

import com.acme.zipi.domain.exceptions.UserNotFoundException;
import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;
import com.acme.zipi.infra.persistence.jpa.entities.UserEntity;
import com.acme.zipi.infra.persistence.jpa.repositories.UsersRepositoryJPA;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryJpaAdapter implements UsersRepository {

    private final UsersRepositoryJPA usersRepositoryJPA;

    @Override
    public User create(User user) {
        this.usersRepositoryJPA.save(convertUser(user));
        return user;
    }

    @Override
    public List<User> getAll() {
        return this.usersRepositoryJPA.findAll().stream()
                .map(this::convertEntity)
                .collect(Collectors.toList());
    }

    @Override
    public User getById(String userId) {
        try {
            return convertEntity(this.usersRepositoryJPA.getById(UUID.fromString(userId)));
        } catch (EntityNotFoundException e) {
            throw new UserNotFoundException(userId);
        }

    }

    @Override
    public void delete(String userId) {
        try {
            this.usersRepositoryJPA.deleteById(UUID.fromString(userId));
        } catch (EntityNotFoundException | EmptyResultDataAccessException e) {
            throw new UserNotFoundException(userId);
        }

    }

    @Override
    public void update(String userId, User user) {

        try {
            UserEntity userEntityFounded = this.usersRepositoryJPA.getById(UUID.fromString(userId));
            userEntityFounded = convertUser(user);
            this.usersRepositoryJPA.save(userEntityFounded);
        } catch (JpaObjectRetrievalFailureException e) {
            throw new UserNotFoundException(userId);
        }

    }

    private User convertEntity(UserEntity entity) {
        return User.builder().userId(entity.getUserId().toString()).age(entity.getAge()).name(entity.getName()).build();
    }

    private UserEntity convertUser(User user) {
        return UserEntity.builder()
                .userId(UUID.fromString(user.getUserId()))
                .name(user.getName())
                .age(user.getAge())
                .build();
    }

}
