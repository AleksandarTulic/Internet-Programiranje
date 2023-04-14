package com.example.eshop.validation;

import com.example.eshop.models.SupportMessage;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ValidationSupportMessage extends Validation implements IValidation{
    private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("title", "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$");
        put("message", "^.{1,1000}$");
    }};

    private SupportMessage supportMessage;

    @Override
    public boolean check() {
        return super.checkRegex(supportMessage.getTitle(), mapRegex.get("title")) &&
                super.checkRegex(supportMessage.getMessage(), mapRegex.get("message"));
    }
}
