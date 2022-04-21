package com.acme.zipi.application;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UsersRepository usersRepository;

    public CreateUserResponse execute(CreateUserRequest request) {
        User user = User.builder().userId(UUID.randomUUID().toString()).name(request.name).age(request.age).build();
        User createdUser = this.usersRepository.create(user);

        CreateUserResponse createUserResponse = CreateUserResponse.builder().userId(createdUser.getUserId()).build();
        return createUserResponse;
    }

}
