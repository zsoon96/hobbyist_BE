package com.sparta.miniproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class MiniProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiniProjectApplication.class, args);
    }

    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
