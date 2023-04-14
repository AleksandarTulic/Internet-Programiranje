package validation;

import java.util.HashMap;
import java.util.Map;

import models.dto.Category;
import models.dto.CategoryField;

public class ValidationCategory extends Validation implements IValidation{
	@SuppressWarnings("serial")
	private static final Map<String, String> mapRegex = new HashMap<String, String>(){{
        put("title", "^[a-zA-Z0-9]{1}[a-zA-Z0-9\\s]{1,98}$");
        put("field_name", "^[a-zA-Z0-9]{2,100}$");
    }};
	
	private Category category;
	
	public ValidationCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public boolean check() {
		for (CategoryField i : category.getFields())
			if (!super.checkRegex(i.getName(), mapRegex.get("field_name")))
				return false;

		return super.checkRegex(category.getTitle(), mapRegex.get("title"));
	}

}
