package com.example.eshop.controllers;

import com.example.eshop.models.ConfirmationTokenRequest;
import com.example.eshop.models.RegistrationRequest;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.LogService;
import com.example.eshop.services.RegistrationService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/registration")
@CrossOrigin("http://localhost:1510/")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final UserService userService;
    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @PostMapping(consumes = {"multipart/form-data"})
    public Integer register(@Valid @ModelAttribute RegistrationRequest request){
        logService.save("Class: RegistrationController. Method: register. Register new account.", LogService.LEVEL_INFO, null);

        return registrationService.register(request);
    }

    /*
     * User needs to be logged in.
     * You need to be logged in. But without the confirmation token you can do nothing more than ordinary operations(the same operations you could do without account)
     * */
    @PostMapping(path = "confirm")
    public ResponseEntity<String> confirm(@RequestBody ConfirmationTokenRequest confirmationTokenRequest, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: RegistrationController. Method: confirm. Confirm your account using token value.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(registrationService.confirm(confirmationTokenRequest.getUsername(), confirmationTokenRequest.getToken()), HttpStatus.OK);
    }
}
