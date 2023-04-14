package com.example.eshop.controllers;

import com.example.eshop.models.ConsumerResponse;
import com.example.eshop.models.RegistrationRequest;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.ConsumerService;
import com.example.eshop.services.LogService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "consumer")
@AllArgsConstructor
@CrossOrigin("http://localhost:1510/")
public class ConsumerController {
    private final ConsumerService consumerService;

    private final UserService userService;

    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @GetMapping(path = "{username}/{avatar}")
    public ResponseEntity<List<byte[]>> retrieveAvatar(@PathVariable("username") String username, @PathVariable("avatar") String avatar){
        logService.save("Class: ConsumerController. Method: retrieveAvatar. Get consumer image/avatar by given username and picture name.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(Arrays.asList(consumerService.retrieveAvatar(username, avatar)), HttpStatus.OK);
    }

    /*
     * Everyone can use this.
     * It Returns ConsumerResponse not ConsumerEntity(that means it will not return hashed password).
     * */
    @GetMapping(path = "{username}")
    public ResponseEntity<ConsumerResponse> retrieveByUsername(@PathVariable("username") String username){
        logService.save("Class: ConsumerController. Method: retrieveByUsername. Get consumer information by given username.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(consumerService.retrieveByUsername(username), HttpStatus.OK);
    }

    /*
     * User needs to be logged in
     * */
    @PutMapping(path="update", consumes = {"multipart/form-data"})
    public ResponseEntity<ConsumerResponse> update(@ModelAttribute RegistrationRequest consumer, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: ConsumerController. Method: retrieveByUsername. Get consumer information by given username.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(consumerService.update(consumer, principal.getName()), HttpStatus.OK);
    }
}
