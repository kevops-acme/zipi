package com.acme.zipi.application.users.getById;

import com.acme.zipi.domain.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserByIdUseCaseResponse {

    private final User user;

}
