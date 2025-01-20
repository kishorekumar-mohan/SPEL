 package com.example;

import java.io.IOException;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class JsonLoader {

    private Map<String, Object> jsonData;

    @PostConstruct
    public void loadJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

         ClassPathResource resource = new ClassPathResource("config.json");

        jsonData = objectMapper.readValue(resource.getInputStream(), new TypeReference<Map<String, Object>>() {});
    }

    public Map<String, Object> getJsonData() {
        return jsonData;
    }
}
