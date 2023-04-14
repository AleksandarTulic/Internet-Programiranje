package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.LogService;

import java.io.IOException;

import com.google.gson.Gson;

import controller.specialController.CheckLoginServlet;

@WebServlet("/log/*")
public class LogController extends CheckLoginServlet {
	private static final long serialVersionUID = 1L;
    private LogService service = new LogService();
    
    public LogController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (check(request)) {
			String what = request.getParameter("what");
			
			if ("getNumberOfLogs".equals(what)) {
				String searchBy = request.getParameter("searchBy");
				
				Long res = service.selectNumberOfLogs(searchBy);
				String json = new Gson().toJson("{\"number\": " + res + "}");
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("selectLogs".equals(what)) {
				String searchBy = request.getParameter("searchBy");
				Long left = 0L;
				Long right = 0L;
				
				try {
					left = Long.parseLong(request.getParameter("left"));
					right = Long.parseLong(request.getParameter("right"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				//System.out.println(searchBy + " " + left + " " + right);
				String json = new Gson().toJson(service.select(searchBy, left, right));
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}

}
