package com.acme.zipi.infra.rest;

import com.acme.zipi.application.users.create.CreateUserRequest;
import com.acme.zipi.application.users.create.CreateUserResponse;
import com.acme.zipi.application.users.create.CreateUserUseCase;
import com.acme.zipi.application.users.delete.DeleteUserUseCase;
import com.acme.zipi.application.users.getById.GetUserByIdUseCase;
import com.acme.zipi.application.users.getById.GetUserByIdUseCaseRequest;
import com.acme.zipi.application.users.getById.GetUserByIdUseCaseResponse;
import com.acme.zipi.application.users.getall.GetAllUsersResponse;
import com.acme.zipi.application.users.getall.GetAllUsersUseCase;
import com.acme.zipi.application.users.update.UpdateUserUseCase;
import com.acme.zipi.domain.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final CreateUserUseCase createUserUseCase;
    private final GetAllUsersUseCase allUsersUseCase;
    private final GetUserByIdUseCase byIdUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody UserDTO userDTO) {

        CreateUserRequest createUserRequest = CreateUserRequest.builder()
                .name(userDTO.name).age(userDTO.age).build();

        CreateUserResponse createUserResponse = this.createUserUseCase.execute(createUserRequest);

        CreateUserResponse createUserHttpResponse = CreateUserResponse
                .builder().userId(createUserResponse.getUserId()).build();

        return new ResponseEntity<CreateUserResponse>(createUserHttpResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<GetAllUsersResponse> getAllUsers() {

        return new ResponseEntity<GetAllUsersResponse>(allUsersUseCase.execute(), HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<GetUserByIdUseCaseResponse> getUserById(@PathVariable String userId) {
        GetUserByIdUseCaseRequest request = GetUserByIdUseCaseRequest.builder().userId(userId).build();
        return new ResponseEntity<GetUserByIdUseCaseResponse>(byIdUseCase.execute(request), HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable String userId) {
        this.deleteUserUseCase.execute(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{userId}")
    public ResponseEntity updateUser(@PathVariable String userId, @RequestBody UserDTO userDTO) {
        User user = User.builder().userId(userId).name(userDTO.name).age(userDTO.age).build();
        this.updateUserUseCase.execute(userId, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
