package com.example.eshop.validation;

import java.util.regex.Pattern;

public abstract class Validation {
    protected boolean checkRegex(String value, String regex){
        Pattern REGEX_PATTERN = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return REGEX_PATTERN.matcher(value).find();
    }
}
