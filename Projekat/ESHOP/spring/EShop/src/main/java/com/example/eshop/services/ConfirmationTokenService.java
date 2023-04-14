package com.example.eshop.services;

import com.example.eshop.repositories.ConfirmationTokenRepository;
import com.example.eshop.models.entities.ConfirmationTokenEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationTokenEntity token) {
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationTokenEntity> getToken(Long consumerId) {
        return confirmationTokenRepository.findByConsumerId(consumerId);
    }

    public int setConfirmedAt(Long consumerId) {
        return confirmationTokenRepository.updateConfirmedAt(consumerId, Timestamp.valueOf(LocalDateTime.now()));
    }
}
