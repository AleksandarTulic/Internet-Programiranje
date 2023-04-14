package com.example.eshop.controllers;

import com.example.eshop.models.LoginRequest;
import com.example.eshop.models.LoginResponse;
import com.example.eshop.services.LogService;
import com.example.eshop.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "user")
@AllArgsConstructor
@CrossOrigin("http://localhost:1510/")
public class UserController {
    private final UserService userService;
    private final LogService logService;

    /*
     * Everyone can use this.
     * */
    @PostMapping(path = "login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        logService.save("Class: UserController. Method: login. Login with username and password.", LogService.LEVEL_INFO, null);

        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }
}
