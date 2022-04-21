package com.acme.zipi.infra.persistence.jpa.adapters;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;
import com.acme.zipi.infra.persistence.jpa.entities.UserEntity;
import com.acme.zipi.infra.persistence.jpa.repositories.UsersRepositoryJPA;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsersRepositoryJpaAdapter implements UsersRepository {

    private final UsersRepositoryJPA usersRepositoryJPA;

    @Override
    public User create(User user) {
        UserEntity userEntity = UserEntity.builder()
                .userId(UUID.fromString(user.getUserId()))
                .name(user.getName())
                .age(user.getAge())
                .build();
        this.usersRepositoryJPA.save(userEntity);
        return user;
    }
}
