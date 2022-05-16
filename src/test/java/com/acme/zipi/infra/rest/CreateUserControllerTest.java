package com.acme.zipi.infra.rest;

import com.acme.zipi.domain.model.User;
import com.acme.zipi.domain.repositories.UsersRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateUserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UsersRepository usersRepository;

    @Test
    public void shouldCreateUserFromHTTP() throws Exception {

        String userId = UUID.randomUUID().toString();
        String name = "Rubén Aguilera";
        Integer age = 39;

        User userCreated = User.builder().userId(userId).name(name).age(age).build();
        Mockito.when(usersRepository.create(any())).thenReturn(userCreated);

        UserDTO createUserHttpRequest = UserDTO.builder()
                .name(name)
                .age(age)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String requestJSON = objectMapper.writeValueAsString(createUserHttpRequest);

        mockMvc.perform(post("/v1/users").contentType(MediaType.APPLICATION_JSON).content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"userId\":" + userId + "}"))
                .andReturn();
    }

}
