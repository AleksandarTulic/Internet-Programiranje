package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.dto.Consumer;
import service.ConsumerService;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;

import controller.specialController.CheckLoginServlet;

@WebServlet("/user/*")
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UserController extends CheckLoginServlet {
	private static final long serialVersionUID = 1L;
	private ConsumerService service = new ConsumerService();
	
    public UserController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (check(request)) {
			String what = request.getParameter("what");
			
			if ("insertUser".equals(what)) {
				Part filePart = request.getPart("insertFile");
				String firstName = request.getParameter("insertFirstName");
				String lastName = request.getParameter("insertLastName");
				String username = request.getParameter("insertUsername");
				String password = request.getParameter("insertPassword");
				String email = request.getParameter("insertEmail");
				String phone = request.getParameter("insertPhone");
				String city = request.getParameter("insertCity");
				
				Consumer c = new Consumer(null, firstName, lastName, username, password, email, phone, city, filePart.getSubmittedFileName());
				boolean flag = false;
				flag = service.insert(c);
				
				if (flag) {
					try {
						File f = new File(Navigation.PATH_SAVE + File.separator + username);
						if (f.exists()) {
							deleteFilesOrFolders(f);
						}
						
						f.mkdir();
						
						if (filePart.getSize() > 0) {
							filePart.write(Navigation.PATH_SAVE + File.separator + username + File.separator + filePart.getSubmittedFileName());
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				String json = "{\"result\": \"" + flag + "\"}";
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("selectUsers".equals(what)) {
				List<Consumer> arr = new ArrayList<>();
				
				try {
					arr = service.select(request.getParameter("firstNameOrLastName"), Long.parseLong(request.getParameter("left")), Long.parseLong(request.getParameter("right")));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				String json = new Gson().toJson(arr);

				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("getNumberOfUsers".equals(what)) {
				String json = "{\"number\": 0}";
				
				try {
					Long res = service.selectNumberOfUsers(request.getParameter("firstNameOrLastName"));
					json = new Gson().toJson("{\"number\": " + res + "}");
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("deleteUser".equals(what)) {
				Long id = -1L;
				
				try {
					id = Long.parseLong(request.getParameter("id"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				String json = "{\"result\": \"false\"}";
				
				if (id >= 1) {
					boolean flag = service.delete(id);
					json = "{\"result\": \"" + flag + "\"}";
				}
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("updateUser".equals(what)) {
				Part filePart = request.getPart("updateFile");
				String idString = request.getParameter("updateId");
				String firstName = request.getParameter("updateFirstName");
				String lastName = request.getParameter("updateLastName");
				String username = request.getParameter("updateUsername");
				String password = request.getParameter("updatePassword");
				String email = request.getParameter("updateEmail");
				String phone = request.getParameter("updatePhone");
				String city = request.getParameter("updateCity");
				Long id = -1L;
				
				try {
					id = Long.parseLong(idString);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				Consumer c = new Consumer(id, firstName, lastName, username, password, email, phone, city, filePart.getSubmittedFileName());
				boolean flag = false;
				flag = service.update(c);
				
				//ovo provjera sa null ne radi moras na drugaciji nacin sutra
				//!!!!!!!!!!!!!!!!!!!!!!!!!!
				if (flag && filePart != null) {
					try {
						File f = new File(Navigation.PATH_SAVE + File.separator + username);
						if (!f.exists())
							f.mkdir();
						
						if (filePart.getSize() > 0) {
								filePart.write(Navigation.PATH_SAVE + File.separator + username + File.separator + filePart.getSubmittedFileName());
							
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				String json = "{\"result\": \"" + flag + "\"}";
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}else if ("getUserPicturePath".equals(what)){
				String avatar = request.getParameter("avatar");
				String username = request.getParameter("username");
				
				String imagePath = Navigation.PATH_SAVE + File.separator + username + File.separator + avatar;
				byte[] fileContent = FileUtils.readFileToByteArray(new File(imagePath));
				String encodedString = Base64.getEncoder().encodeToString(fileContent);
				
				String json = "{\"result\": \"" + encodedString + "\"}";
				
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			}
		}
	}
	
	private void deleteFilesOrFolders(File f) {
		if (f.isFile()) {
			f.delete();
		}else {
			File[] arr = f.listFiles();
			for (File i : arr) {
				deleteFilesOrFolders(i);
			}
			
			f.delete();
		}
	}

}
