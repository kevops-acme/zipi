package com.acme.zipi.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    String name;
    Integer age;

}
