package com.acme.zipi.infra.rest;

import com.acme.zipi.application.CreateUserRequest;
import com.acme.zipi.application.CreateUserResponse;
import com.acme.zipi.application.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class CreateUserController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<CreateUserHttpResponse> createUser(@RequestBody CreateUserHttpRequest createUserHttpRequest) {

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .name(createUserHttpRequest.name).age(createUserHttpRequest.age).build();

        CreateUserResponse createUserResponse = this.createUserUseCase.execute(createUserRequest);

        CreateUserHttpResponse createUserHttpResponse = CreateUserHttpResponse
                .builder().userId(createUserResponse.getUserId()).build();

        return new ResponseEntity<CreateUserHttpResponse>(createUserHttpResponse, HttpStatus.CREATED);
    }
}
