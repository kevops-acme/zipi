package com.acme.zipi.application.users.update;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {

    private final UsersRepository usersRepository;

    public void execute(String userId, User user) {
        this.usersRepository.update(userId, user);
    }

}
