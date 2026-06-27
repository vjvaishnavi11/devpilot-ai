package com.vaishnavi.devpilot.controller;

import com.vaishnavi.devpilot.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ApiResponse<Map<String, Object>> health(){
        Map<String, Object> data = Map.of(
                "status", "UP",
                "application", "DevPilot AI",
                "version", "1.0.0",
                "timestamp", LocalDateTime.now()
        );

        return new ApiResponse<>(
                true,
                "Application is running",
                data
        );
    }
}
