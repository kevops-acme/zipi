package com.acme.zipi.application.users.getall;

import java.util.List;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {

    private final UsersRepository usersRepository;

    public GetAllUsersResponse execute() {

        List<User> users =  usersRepository.getAll();
        return GetAllUsersResponse.builder().users(users).build();

    }
}
