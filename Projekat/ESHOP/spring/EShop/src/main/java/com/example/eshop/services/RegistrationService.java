package com.example.eshop.services;

import com.example.eshop.models.RegistrationRequest;
import com.example.eshop.models.entities.ConfirmationTokenEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.services.email.EmailSender;
import com.example.eshop.validation.ValidationRegistration;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final LogService logService;

    public Integer register(RegistrationRequest request) {
        if (!new ValidationRegistration(request).check()) {
            logService.save("Class: RegistrationService. Method: register. Reason: Not valid parameter values.", LogService.LEVEL_DANGER, null);

            throw new IllegalStateException("Not valid parameter values.");
        }

        Integer token = userService.singUpUser(request);
        emailSender.send(request.getEmail(), emailSender.buildEmail(request.getFirstName(), token + ""));

        return token;
    }

    public String confirm(String username, Long token){
        UserEntity userEntity = userService.findByUsername(username);
        ConfirmationTokenEntity confirmationTokenEntity = confirmationTokenService.getToken(userEntity.getId()).orElseThrow(() -> new IllegalStateException("Token not found."));

        if (confirmationTokenEntity.getConfirmedAt() != null) {
            logService.save("Class: RegistrationService. Method: confirm. Reason: Token already confirmed.", LogService.LEVEL_DANGER, null);

            throw new IllegalStateException("Token already confirmed.");
        }

        if (confirmationTokenEntity.getToken() == token + 0) {
            LocalDateTime expiredAt = confirmationTokenEntity.getExpiresAt().toLocalDateTime();

            if (expiredAt.isBefore(LocalDateTime.now())) {
                logService.save("Class: RegistrationService. Method: confirm. Reason: Token expired.", LogService.LEVEL_DANGER, null);

                throw new IllegalStateException("Token expired.");
            }

            confirmationTokenService.setConfirmedAt(userEntity.getId());

            return "confirm";
        }

        return null;
    }
}
