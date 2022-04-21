package com.acme.zipi.application.users.create;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    String name;
    Integer age;

}
