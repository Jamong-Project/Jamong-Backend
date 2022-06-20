package com.example.jamong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JamongApplication {

    public static void main(String[] args) {
        SpringApplication.run(JamongApplication.class, args);
    }

}
