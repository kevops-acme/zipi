package com.acme.zipi.application.users.getById;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserByIdUseCaseRequest {

    public final String userId;

}
