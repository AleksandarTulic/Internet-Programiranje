package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import models.dto.Category;
import models.dto.CategoryField;
import validation.ValidationCategory;
import models.dao.CategoryDAO;

public class CategoryService {
	private static Map<String, Function<String, String>> map = new HashMap<>();
	private static Map<String, Boolean> mapFlag = new HashMap<>();
	private static final String YOU_SHALL_NOT_PASS = "-";
	private CategoryDAO dao = new CategoryDAO();
	
	static {
		Function<String, String> a = (String regex) -> {
			return "^[a-zA-Z0-9]{1,1}[a-zA-Z\\.0-9\\s]{1,99}$";
		};
		
		Function<String, String> b = (String regex) -> {
			return regex.split("-")[0] + "|" + regex.split("-")[1];
		};
		
		Function<String, String> c = (String regex) -> {
			String []sp = regex.split("-")[2].split("\\|");
			String res = "";
			
			for (int i=0;i<sp.length;i++)
				res += "^" + sp[i]  + (i == sp.length - 1 ? "$" : "$|");

			return res;
		};
		
		map.put("TEXT", a);
		map.put("NUMBER", b);
		map.put("FIXED", c);
		mapFlag.put("TEXT", false);
		mapFlag.put("NUMBER", false);
		mapFlag.put("FIXED", true);
	}
	
	public boolean delete(String title) {
		return dao.delete(title);
	}
	
	public List<Category> select(Long left, Long right){
		return dao.select(left, right);
	}
	
	public Long selectNumberOfCategories() {
		return dao.selectNumberOfCategories();
	}
	
	public boolean insert(Category c) {
		configure(c);
		
		boolean flag = false;
		if (new ValidationCategory(c).check())
			flag = dao.insert(c);
		
		return flag;
	}
	
	private void configure(Category c) {
		for (CategoryField i : c.getFields()) {
			i.setFlagFixedMultipleValues(mapFlag.get(i.getFieldType()));
			i.setRegex(map.get(i.getFieldType()).apply(i.getRegex()));
		}
	}
	
	public boolean update(Category c) {
		String oldTitle = c.getTitle().split("-")[1];
		c.setTitle(c.getTitle().split("-")[0]);
		
		Category cForValidation1 = new Category(c.getTitle());
		cForValidation1.setFields(c.getFields().stream().filter(t -> (YOU_SHALL_NOT_PASS.equals(t.getFieldType()) && YOU_SHALL_NOT_PASS.equals(t.getRegex()))).map(t -> new CategoryField(t.getName().split("-")[0],
				"", "", false)).collect(Collectors.toList()));
		
		Category cForValidation2 = new Category(c.getTitle(), c.getFields().stream().filter(t -> !(YOU_SHALL_NOT_PASS.equals(t.getFieldType()) 
				&& YOU_SHALL_NOT_PASS.equals(t.getRegex()))).map(t -> new CategoryField(t.getName().split("-")[0],
				"", "", false)).collect(Collectors.toList()));
		
		boolean flag = false;
		if (new ValidationCategory(cForValidation1).check() && new ValidationCategory(cForValidation2).check())
			flag = dao.update(c, oldTitle);
		
		if (flag) {
			c.setFields(c.getFields().stream().filter(t -> !(YOU_SHALL_NOT_PASS.equals(t.getFieldType()) && YOU_SHALL_NOT_PASS.equals(t.getRegex()))).collect(Collectors.toList()));
			configure(c);
			
			insertOnlyCategories(c.getTitle(), c.getFields());
		}
		
		return flag;
	}
	
	private boolean insertOnlyCategories(String title, List<CategoryField> arr) {
		return dao.insertOnlyCategories(title, arr);
	}
}
