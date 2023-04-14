package models.dto;

public class CategoryField {
	private String name;
	private String fieldType;
	private String regex;
	private boolean flagFixedMultipleValues;
	
	public CategoryField() {
	}
	
	public CategoryField(String name, String fieldType, String regex, boolean flagFixedMultipleValues) {
		this.name = name;
		this.fieldType = fieldType;
		this.regex = regex;
		this.flagFixedMultipleValues = flagFixedMultipleValues;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
	
	public void setFlagFixedMultipleValues(boolean flagFixedMultipleValues) {
		this.flagFixedMultipleValues = flagFixedMultipleValues;
	}
	
	public boolean getFlagFixedMultipleValues() {
		return flagFixedMultipleValues;
	}

	@Override
	public String toString() {
		return "CategoryField [name=" + name + ", fieldType=" + fieldType + ", regex=" + regex
				+ ", flagFixedMultipleValues=" + flagFixedMultipleValues + "]";
	}
	
}
