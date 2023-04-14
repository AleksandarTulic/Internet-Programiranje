package validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationMessage extends Validation implements IValidation{

	@SuppressWarnings("serial")
	private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("message", "^.{1,1000}$");
    }};
	
	private String message;
	
	public ValidationMessage(String message) {
		this.message = message;
	}
	
	@Override
	public boolean check() {
		return super.checkRegex(message, mapRegex.get("message"));
	}
	
}
