package com.acme.zipi.application.users.delete;

import com.acme.zipi.domain.exceptions.UserNotFoundException;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {

    private final UsersRepository usersRepository;

    public void execute(String userId) {

            this.usersRepository.delete(userId);

    }
    
}
