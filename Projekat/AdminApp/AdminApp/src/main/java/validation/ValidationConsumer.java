package validation;

import java.util.*;

import models.dto.Consumer;

public class ValidationConsumer extends Validation implements IValidation {
	@SuppressWarnings("serial")
	private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("firstName", "^[a-zA-Z]{2,100}$");
        put("lastName", "^[a-zA-Z]{2,100}$");
        put("username", "^[a-zA-Z0-9]{2,50}$");
        put("password", "^[a-zA-Z0-9]{6,50}$");
        put("email", "^[a-zA-Z0-9_]+@[a-zA-Z0-9_]+\\.[a-zA-Z0-9_]{2,4}$");
        put("phone", "^[0-9]{2,50}$");
        put("city", "^[a-zA-Z0-9]{1,1}[a-zA-Z0-9\\s]{1,98}$");
    }};

    private Consumer consumer;
    
    public ValidationConsumer(Consumer consumer) {
    	this.consumer = consumer;
    }
    
	@Override
    public boolean check() {
        return super.checkRegex(consumer.getFirstName(), mapRegex.get("firstName")) &&
                super.checkRegex(consumer.getLastName(), mapRegex.get("lastName")) &&
                super.checkRegex(consumer.getUsername(), mapRegex.get("username")) &&
                super.checkRegex(consumer.getPassword(), mapRegex.get("password")) &&
                super.checkRegex(consumer.getEmail(), mapRegex.get("email")) &&
                super.checkRegex(consumer.getPhone(), mapRegex.get("phone")) &&
                super.checkRegex(consumer.getCity(), mapRegex.get("city"));
    }
}
