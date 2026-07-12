package com.factbody.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BodyFactsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BodyFactsApplication.class, args);
    }
}
