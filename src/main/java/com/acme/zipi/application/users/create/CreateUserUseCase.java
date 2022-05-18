package com.acme.zipi.application.users.create;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    @Value("${app.baseurl.zipi}")
    String baseUrlZipi;

    private final UsersRepository usersRepository;

    public CreateUserResponse execute(CreateUserRequest request) {
        User user = User.builder().userId(UUID.randomUUID().toString()).name(request.name).age(request.age).build();
        User createdUser = this.usersRepository.create(user);

        CreateUserResponse createUserResponse = CreateUserResponse.builder()
        .userId(createdUser.getUserId())
        .link(String.format("%s/v1/users/%s", this.baseUrlZipi, createdUser.getUserId()))
        .build();
        return createUserResponse;
    }

}
