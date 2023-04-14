package com.example.eshop.services;

import com.example.eshop.exceptions.UnAuthorizedException;
import com.example.eshop.models.LoginRequest;
import com.example.eshop.models.LoginResponse;
import com.example.eshop.models.RegistrationRequest;
import com.example.eshop.models.entities.ConfirmationTokenEntity;
import com.example.eshop.models.entities.ConsumerEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.repositories.ConsumerRepository;
import com.example.eshop.repositories.UserRepository;
import com.example.eshop.services.email.EmailSender;
import com.example.eshop.services.utilities.FileService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user with username %s not found";
    private final Integer MAX_TOKEN_VALUE = 9999;
    private final Integer MIN_TOKEN_VALUE = 1000;
    private final UserRepository userRepository;
    private final ConsumerRepository consumerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final FileService fileService;
    private final EmailSender emailSender;
    private final LogService logService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        boolean isPresent = false;

        if (userEntity.isPresent())
            isPresent = consumerRepository.findById(userEntity.get().getId()).isPresent();

        if (isPresent)
            return userEntity.get();

        logService.save("Class: UserService. Method: singUpUser. Reason = " + String.format(USER_NOT_FOUND, username), LogService.LEVEL_INFO, null);
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND, username));
    }

    public Integer singUpUser(RegistrationRequest request) {
        boolean userExists = userRepository.findByUsername(request.getUsername()).isPresent();

        if (userExists) {
            logService.save("Class: UserService. Method: singUpUser. Reason = Username already taken.", LogService.LEVEL_INFO, null);
            throw new IllegalStateException("Username already taken.");
        }

        String encoded = bCryptPasswordEncoder.encode(request.getPassword());
        request.setPassword(encoded);

        Integer token = (new Random()).nextInt(MAX_TOKEN_VALUE - MIN_TOKEN_VALUE + 1) + MIN_TOKEN_VALUE;

        UserEntity userEntity = userRepository.save(new UserEntity(null, request.getFirstName(), request.getLastName(), request.getUsername(), request.getPassword()));
        ConsumerEntity consumerEntity = new ConsumerEntity(userEntity.getId(), request.getEmail(), request.getPhone(), request.getCity(), (request.getAvatar() == null || "".equals(request.getAvatar().getOriginalFilename())) ? null : request.getAvatar().getOriginalFilename());

        consumerRepository.save(consumerEntity);
        confirmationTokenService.saveConfirmationToken(new ConfirmationTokenEntity(userEntity.getId(), null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), token));

        if (request.getAvatar() != null)
            if (!"".equals(request.getAvatar().getOriginalFilename()))
                fileService.saveFile(request.getUsername(), request.getAvatar());

        return token;
    }

    public UserEntity findByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public LoginResponse login(LoginRequest loginRequest){
        Optional<UserEntity> userEntity = userRepository.findByUsername(loginRequest.getUsername());

        if (userEntity.isPresent() && bCryptPasswordEncoder.matches(loginRequest.getPassword(), userEntity.get().getPassword())) {
            Optional<ConfirmationTokenEntity> confirmationTokenEntity = confirmationTokenService.getToken(userEntity.get().getId());

            LoginResponse loginResponse = new LoginResponse(userEntity.get().getId(), userEntity.get().getUsername(), loginRequest.getPassword(), false);
            if (confirmationTokenEntity.isPresent()) {
                if (confirmationTokenEntity.get().getConfirmedAt() != null) {
                    loginResponse.setFlag(true);
                }else{
                    sendTokenAgain(userEntity);
                }
            }else{
                sendTokenAgain(userEntity);
            }

            return loginResponse;
        }else{
            logService.save("Class: UserService. Method: login. Reason = Failed login.", LogService.LEVEL_INFO, null);
            throw new UnAuthorizedException("Failed login.");
        }
    }

    private void sendTokenAgain(Optional<UserEntity> userEntity){
        Integer token = (new Random()).nextInt(MAX_TOKEN_VALUE - MIN_TOKEN_VALUE + 1) + MIN_TOKEN_VALUE;
        confirmationTokenService.saveConfirmationToken(new ConfirmationTokenEntity(userEntity.get().getId(), null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), token));
        ConsumerEntity consumerEntity = consumerRepository.findById(userEntity.get().getId()).get();
        emailSender.send(consumerEntity.getEmail(), emailSender.buildEmail(userEntity.get().getFirstName(), token + ""));
    }
}
