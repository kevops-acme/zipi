package com.acme.zipi.application.users.getById;

import javax.persistence.EntityNotFoundException;

import com.acme.zipi.domain.exceptions.UserNotFoundException;
import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetUserByIdUseCase {

    private final UsersRepository usersRepository;

    public GetUserByIdUseCaseResponse execute(GetUserByIdUseCaseRequest request) {
        User userFounded = this.usersRepository.getById(request.userId);
        return GetUserByIdUseCaseResponse.builder().user(userFounded).build();

    }

}
