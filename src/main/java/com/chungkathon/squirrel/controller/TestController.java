package com.chungkathon.squirrel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @GetMapping("/test")
    public String testConnection() {
        // JSON 응답을 위한 Map 사용
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, World! Test API is working.");

        try {
            // ObjectMapper를 사용해 Map을 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(response);  // Map을 JSON 문자열로 변환하여 반환
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"error\": \"Error occurred while converting to JSON\"}";
        }
    }
}