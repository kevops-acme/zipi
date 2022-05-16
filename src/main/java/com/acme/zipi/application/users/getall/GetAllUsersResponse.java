package com.acme.zipi.application.users.getall;

import java.util.List;

import com.acme.zipi.domain.model.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllUsersResponse {

    List<User> users;

}
