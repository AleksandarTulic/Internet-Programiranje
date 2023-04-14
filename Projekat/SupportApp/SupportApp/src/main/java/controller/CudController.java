package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.dto.Consumer;
import models.dto.SupportSpecialist;
import service.EmailService;
import service.SupportMessageService;

import java.io.IOException;

import com.google.gson.Gson;


/*
 * ******************************************************************
 * *                                                                *
 * * EVERY OPRATION EXCEPT RETRIEVE(ALL THAT IS IN DATA CONTROLLER) *
 * *                                                                *
 * ******************************************************************
 * */
@WebServlet("/cud/*")
public class CudController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SupportMessageService service = new SupportMessageService();
	private EmailService emailService = new EmailService();
	
    public CudController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String what = request.getParameter("what");
		
		try {
			if ("updateRead".equals(what)) {
				String idString = request.getParameter("id");
				
				Long id = Long.parseLong(idString);
				Boolean flag = service.updateRead(id, true);
				String json = new Gson().toJson(flag);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("updateReturnMessage".equals(what)) {
				String idString = request.getParameter("id");
				String return_message = request.getParameter("return_message");
				String email = request.getParameter("email");
				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				
				Consumer c = new Consumer(email, firstName, lastName);
				Long id = Long.parseLong(idString);
				
				HttpSession session = request.getSession();
				SupportSpecialist supportSpecialist = (SupportSpecialist)session.getAttribute("supportSpecialist");
				
				Boolean flag = service.updateReturnMessage(id, supportSpecialist.getId(), return_message);
				if (flag) {
					flag = emailService.send(c, return_message);
				}
				String json = new Gson().toJson(flag);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
