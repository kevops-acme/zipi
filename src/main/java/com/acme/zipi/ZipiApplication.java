package com.acme.zipi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ZipiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipiApplication.class, args);
	}

}
