package com.teleport.tracking.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.teleport.tracking.*")
public class TrackingNumberApplication {
    public static void main(String[] args) {
        SpringApplication.run(TrackingNumberApplication.class, args);
    }
}
