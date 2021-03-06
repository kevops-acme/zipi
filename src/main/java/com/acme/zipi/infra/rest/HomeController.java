package com.acme.zipi.infra.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("")
    ResponseEntity<String> getUsers() {
        return new ResponseEntity<>("Hello World, v4.0!", HttpStatus.OK);
    }

}
