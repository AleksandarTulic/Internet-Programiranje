package com.example.eshop.services;

import com.example.eshop.models.entities.LogEntity;
import com.example.eshop.repositories.LogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LogService {
    public static final String LEVEL_INFO = "INFO";
    public static final String LEVEL_DANGER = "DANGER";
    private final LogRepository logRepository;

    public LogEntity save(String description, String level, Long consumerId){
        LogEntity logEntity = new LogEntity(null, description, level, Timestamp.valueOf(LocalDateTime.now()), consumerId);
        return logRepository.save(logEntity);
    }
}
