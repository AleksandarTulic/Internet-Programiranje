package filter;

import java.util.Arrays;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.dto.SupportSpecialist;

@WebFilter("/*")
public class LoginFilter extends HttpFilter {
	private static final long serialVersionUID = 1L;
	private static final List<String> arrFlag = Arrays.asList("/login.jsp", "/logout.jsp", "/error.jsp", "/login");
	
	public LoginFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
		try {
			HttpSession session = request.getSession();
			SupportSpecialist supportSpecialist = (SupportSpecialist)session.getAttribute("supportSpecialist");
			String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
			
			if (supportSpecialist == null && !arrFlag.contains(path)) {
				RequestDispatcher disp = request.getRequestDispatcher("login.jsp");
				disp.forward(request, response);
			}
			
			chain.doFilter(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init(FilterChain filterConfig) {
	}

}
