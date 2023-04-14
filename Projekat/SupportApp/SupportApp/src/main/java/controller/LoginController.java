package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.dto.SupportSpecialist;
import service.SupportSpecialistService;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ADDRESS = "index.jsp";
	private SupportSpecialistService service = new SupportSpecialistService();
	
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		SupportSpecialist dto = service.select(username, password);
		HttpSession session = request.getSession();
		session.setAttribute("supportSpecialist", dto);
		
		response.sendRedirect(ADDRESS);
	}

}
