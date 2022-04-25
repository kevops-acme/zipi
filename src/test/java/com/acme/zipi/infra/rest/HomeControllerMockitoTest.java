package com.acme.zipi.infra.rest;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class HomeControllerMockitoTest {

    @InjectMocks
    HomeController homeController;

    @Test
    public void testHomeController() {
        ResponseEntity<String> responseEntity = homeController.getUsers();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Hello World, v2.0!", responseEntity.getBody());
    }
    


}
