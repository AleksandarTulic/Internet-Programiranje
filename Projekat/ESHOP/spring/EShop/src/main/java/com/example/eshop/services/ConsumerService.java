package com.example.eshop.services;

import com.example.eshop.exceptions.NotFoundException;
import com.example.eshop.exceptions.ValidationFailedException;
import com.example.eshop.models.ConsumerResponse;
import com.example.eshop.models.RegistrationRequest;
import com.example.eshop.models.entities.ConsumerEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.repositories.ConsumerRepository;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.services.utilities.FileService;
import com.example.eshop.validation.ValidationRegistration;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConsumerService {
    private final FileService fileService;
    private final ConsumerRepository consumerRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final LogService logService;

    public byte[] retrieveAvatar(String username, String avatar){
        return fileService.getFile(username + File.separator + avatar);
    }

    public ConsumerResponse retrieveByUsername(String username){
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isPresent()){
            Optional<ConsumerEntity> consumerEntity = consumerRepository.findById(userEntity.get().getId());
            if (consumerEntity.isPresent()){
                return new ConsumerResponse(userEntity.get().getId(), userEntity.get().getFirstName(), userEntity.get().getLastName(), username,
                        consumerEntity.get().getEmail(), consumerEntity.get().getPhone(), consumerEntity.get().getCity(), fileService.getFile(username + File.separator + consumerEntity.get().getAvatar()));
            }else{
                logService.save("Class: ConsumerService. Method: retrieveByUsername. Reason: Consumer with username=" + username + " does not exist.", LogService.LEVEL_DANGER, null);
                throw new NotFoundException("Consumer with username=" + username + " does not exist.");
            }
        }else{
            logService.save("Class: ConsumerService. Method: retrieveByUsername. Reason: User with username=" + username + " does not exist.", LogService.LEVEL_DANGER, null);
            throw new NotFoundException("Consumer with username=" + username + " does not exist.");
        }
    }

    public ConsumerResponse update(RegistrationRequest consumer, String usernameOfUserLogedIn){
        consumer.setUsername(usernameOfUserLogedIn);
        if (new ValidationRegistration(consumer).check()) {
            //ovo prvo radim kako bih garantovao da se podaci zapravo i mijenjaju nad n-torkom koja predstavlja ulogovanog korisnika
            UserEntity userLogedIn = userRepository.findByUsername(usernameOfUserLogedIn).get(); //ako je doslo do ove tacke onda je korisnik sigurno ulogovan

            UserEntity userEntity = new UserEntity(userLogedIn.getId(), consumer.getFirstName(), consumer.getLastName(), usernameOfUserLogedIn, bCryptPasswordEncoder.encode(consumer.getPassword()));
            userRepository.save(userEntity);

            ConsumerEntity consumerEntity = new ConsumerEntity(userLogedIn.getId(), consumer.getEmail(), consumer.getPhone(), consumer.getCity(), consumer.getAvatar() == null ? null : consumer.getAvatar().getOriginalFilename());
            consumerRepository.save(consumerEntity);

            if (consumer.getAvatar() != null)
                fileService.saveFile(usernameOfUserLogedIn, consumer.getAvatar());

            return new ConsumerResponse(userLogedIn.getId(), consumer.getFirstName(), consumer.getLastName(),
                    usernameOfUserLogedIn, consumer.getEmail(), consumer.getPhone(), consumer.getCity(),
                    consumer.getAvatar() == null ? null : fileService.getFile(usernameOfUserLogedIn + File.separator + consumer.getAvatar().getOriginalFilename()));
        }else{
            logService.save("Class: ConsumerService. Method: update. Reason: You need to input correct field values.", LogService.LEVEL_DANGER, null);
            throw new ValidationFailedException("You need to input correct field values.");
        }
    }
}
