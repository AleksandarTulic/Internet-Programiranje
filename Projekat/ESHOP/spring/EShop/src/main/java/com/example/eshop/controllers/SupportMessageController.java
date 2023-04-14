package com.example.eshop.controllers;

import com.example.eshop.models.SupportMessage;
import com.example.eshop.models.entities.SupportMessageEntity;
import com.example.eshop.models.entities.UserEntity;
import com.example.eshop.services.LogService;
import com.example.eshop.services.SupportMessageService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Data
@RestController
@AllArgsConstructor
@RequestMapping(path = "/support_message")
@CrossOrigin("http://localhost:1510/")
public class SupportMessageController {
    private final SupportMessageService supportMessageService;
    private final LogService logService;
    private final UserService userService;

    /*
     * User needs to be logged in.
     * */
    @PostMapping(path = "create")
    public ResponseEntity<SupportMessageEntity> createMessage(@RequestBody SupportMessage supportMessage, Principal principal){
        UserEntity userEntity = userService.findByUsername(principal.getName());
        logService.save("Class: SupportMessageController. Method: createMessage. Create support message.", LogService.LEVEL_INFO, userEntity.getId());

        return new ResponseEntity<>(supportMessageService.save(supportMessage, principal.getName()), HttpStatus.CREATED);
    }
}
