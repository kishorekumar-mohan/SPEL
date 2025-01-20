package com.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SpelExample {

	@Value("#{jsonLoader.jsonData['app']['name']?: 'Unknown App'}")
    private String appName;

    @Value("#{jsonLoader.jsonData['features']['maxUsers'] > 500 ? 'High Capacity' : 'Low Capacity'}")
    private String capacity;

    @PostConstruct
    public void printValues() {
        System.out.println("App Name: " + appName);
        System.out.println("Capacity: " + capacity);
    }
}
