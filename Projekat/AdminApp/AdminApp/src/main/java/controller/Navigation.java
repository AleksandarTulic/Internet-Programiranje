package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import controller.specialController.CheckLoginServlet;

@WebServlet("/navigation/*")
public class Navigation extends CheckLoginServlet {
	private static final long serialVersionUID = 1L;
	private static final String PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + File.separator + "ESHOP";
	public static final String PATH_SAVE = PATH + File.separator + "users";
	
	//TO UE THIS APP USER NEEDS TO GO TO THIS CONTROLLER AT LEAST ONCE
	static {
		File f = new File(PATH);
		if (!f.exists())
			f.mkdir();
		
		f = new File(PATH + File.separator + "users");
		if (!f.exists())
			f.mkdir();
	}
       
    public Navigation() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String what = request.getParameter("what");
		String address = "/WEB-INF/pages/login.jsp";
		
		if (what == null || "".equals(what)) {
			address = "/WEB-INF/pages/login.jsp";
		}else if (check(request)) { 
			if ("logout".equals(what)) {
				request.getSession().invalidate();
				address = "/WEB-INF/pages/login.jsp";
			}else if ("index".equals(what)) {
				address = "/WEB-INF/pages/index.jsp";
			}else if ("users".equals(what)) {
				address = "/WEB-INF/pages/users.jsp";
			}else if ("categories".equals(what)) {
				address = "/WEB-INF/pages/categories.jsp";
			}else if ("logs".equals(what)) {
				address = "/WEB-INF/pages/logs.jsp";
			}else if ("view".equals(what)) {
				address = "/WEB-INF/pages/view.jsp";
			}
		}
		
		RequestDispatcher disp = request.getRequestDispatcher(address);
		disp.forward(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
