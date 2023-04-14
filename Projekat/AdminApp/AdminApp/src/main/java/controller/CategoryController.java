package controller;

import models.dto.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CategoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.specialController.CheckLoginServlet;

@WebServlet("/category/*")
public class CategoryController extends CheckLoginServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService service = new CategoryService();
    
    public CategoryController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (check(request)) {
			String what = request.getParameter("what");
		
			if ("selectCategories".equals(what)) {
				List<Category> arr = new ArrayList<>();
				
				try {
					Long left = Long.parseLong(request.getParameter("left"));
					Long right = Long.parseLong(request.getParameter("right"));
					
					arr = service.select(left, right);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				String json = new Gson().toJson(arr);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("deleteCategory".equals(what)) {
				String title = request.getParameter("title");
				boolean flag = service.delete(title);
				
				String json = "{\"result\": \"" + flag + "\"}";
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("getNumberOfCategories".equals(what)) {
				String json = "{\"number\": 0}";
				Long res = service.selectNumberOfCategories();
				json = new Gson().toJson("{\"number\": " + res + "}");
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("insertCategory".equals(what)) {
				Gson gson = new Gson();
				
				JsonObject jsonObject = new JsonParser().parse(request.getParameter("value")).getAsJsonObject();
				Category c = gson.fromJson(jsonObject, Category.class);
				
				boolean flag = service.insert(c);
				
				String json = "{\"result\": \"" + flag + "\"}";
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("updateCategory".equals(what)) {
				Gson gson = new Gson();
				
				JsonObject jsonObject = new JsonParser().parse(request.getParameter("value")).getAsJsonObject();
				Category c = gson.fromJson(jsonObject, Category.class);
				
				//System.out.println(c);
				boolean flag = service.update(c);
				
				String json = "{\"result\": \"" + flag + "\"}";
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}

}
