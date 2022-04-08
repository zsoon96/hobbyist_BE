package com.sparta.miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.access.annotation.Secured;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniProjectApplication.class, args);
    }

}
