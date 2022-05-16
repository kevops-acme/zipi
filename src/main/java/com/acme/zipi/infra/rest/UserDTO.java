package com.acme.zipi.infra.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    String name;
    Integer age;

}
