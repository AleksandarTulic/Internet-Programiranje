package com.example.eshop.services;

import com.example.eshop.exceptions.ValidationFailedException;
import com.example.eshop.models.SupportMessage;
import com.example.eshop.models.entities.SupportMessageEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.repositories.SupportMessageRepository;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.validation.ValidationSupportMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupportMessageService {
    private final SupportMessageRepository supportMessageRepository;
    private final UserRepository userRepository;
    private final LogService logService;

    public SupportMessageEntity save(SupportMessage supportMessage, String usernameOfUserLogedIn){
        if (new ValidationSupportMessage(supportMessage).check()) {
            UserEntity userEntity = userRepository.findByUsername(usernameOfUserLogedIn).get();

            return supportMessageRepository.save(new SupportMessageEntity(supportMessage.getTitle(), supportMessage.getMessage(), userEntity.getId()));
        }else{
            logService.save("Class: SupportMessageEntity. Method: save. Reason: You need to input correct field values.", LogService.LEVEL_DANGER, null);
            throw new ValidationFailedException("You need to input correct field values.");
        }
    }
}
