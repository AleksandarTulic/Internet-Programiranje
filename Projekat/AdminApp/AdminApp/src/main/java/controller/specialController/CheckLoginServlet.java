package controller.specialController;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import models.dto.Admin;

public class CheckLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected boolean check(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Admin dto = (Admin)session.getAttribute("admin");
		return dto != null;
		
		//true - login already done
		//false - login not done
	}
}
