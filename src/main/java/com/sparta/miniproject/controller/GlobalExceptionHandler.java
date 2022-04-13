package com.sparta.miniproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public Map<String, String> IllegalArgumentHandler(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "false");
        map.put("message", e.getMessage());
        map.put("http", String.valueOf(HttpStatus.BAD_REQUEST));
        return map;
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Map<String, String> NullPointerHandler(Exception e) {
        Map<String, String> map = new HashMap<>();
        map.put("status", "false");
        map.put("message", e.getMessage());
        map.put("http", String.valueOf(HttpStatus.BAD_REQUEST));
        return map;
    }

}
