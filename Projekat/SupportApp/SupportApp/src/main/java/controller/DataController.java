package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.dto.SupportMessage;
import service.SupportMessageService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

@WebServlet("/data/*")
public class DataController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, Byte> mapMessageType = new HashMap<>();
	private SupportMessageService service = new SupportMessageService();
	
	static {
		mapMessageType.put("Read", (byte)1);
		mapMessageType.put("NotRead", (byte)0);
		mapMessageType.put("All", (byte)2);
	}
	
    public DataController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new ServletException();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String what = request.getParameter("what");
		
		try {
			if ("getSupportMessages".equals(what)) {
				String messageType = request.getParameter("messageType");
				String messageContent = request.getParameter("messageContent");
				String left = request.getParameter("leftNumber");
				String right = request.getParameter("rightNumber");
				
				Long l = Long.parseLong(left);
				Long r = Long.parseLong(right);
				byte b = mapMessageType.get(messageType);
				
				List<SupportMessage> arr = service.select(b, messageContent, l, r);
				String json = new Gson().toJson(arr);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("getNumberOfMessages".equals(what)) {
				String messageType = request.getParameter("messageType");
				byte b = mapMessageType.get(messageType);
				String json = new Gson().toJson("{\"number\": " + service.selectNumberOfMessages(b) + "}");
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
