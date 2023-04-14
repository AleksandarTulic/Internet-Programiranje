package com.example.eshop.validation;

import com.example.eshop.models.RegistrationRequest;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ValidationRegistration extends Validation implements IValidation{
    private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("firstName", "^[a-zA-Z]{2,}$");
        put("lastName", "^[a-zA-Z]{2,}$");
        put("username", "^[a-zA-Z0-9]{2,50}$");
        put("password", "^[a-zA-Z0-9]{6,50}$");
        put("email", "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]{2,4}$");
        put("phone", "^[0-9]{2,50}$");
        put("city", "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$");
    }};

    private RegistrationRequest registrationRequest;

    @Override
    public boolean check() {
        return super.checkRegex(registrationRequest.getFirstName(), mapRegex.get("firstName")) &&
                super.checkRegex(registrationRequest.getLastName(), mapRegex.get("lastName")) &&
                super.checkRegex(registrationRequest.getUsername(), mapRegex.get("username")) &&
                super.checkRegex(registrationRequest.getPassword(), mapRegex.get("password")) &&
                super.checkRegex(registrationRequest.getEmail(), mapRegex.get("email")) &&
                super.checkRegex(registrationRequest.getPhone(), mapRegex.get("phone")) &&
                super.checkRegex(registrationRequest.getCity(), mapRegex.get("city"));
    }
}
