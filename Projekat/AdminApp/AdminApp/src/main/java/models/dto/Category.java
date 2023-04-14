package models.dto;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String title;
	private List<CategoryField> fields = new ArrayList<>();
	
	public Category() {
	}
	
	public Category(String title) {
		this.title = title;
	}
	
	public Category(String title, List<CategoryField> fields) {
		super();
		this.title = title;
		this.fields = fields;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<CategoryField> getFields() {
		return fields;
	}

	public void setFields(List<CategoryField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "Category [title=" + title + ", fields=" + getFieldsString() + "]";
	}
	
	private String getFieldsString() {
		String res = "";
		for (CategoryField i : fields) {
			res += i.toString();
		}
		
		return res;
	}
}
