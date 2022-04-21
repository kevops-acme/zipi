package com.acme.zipi.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private final String userId;
    private final String name;
    private final Integer age;

}
