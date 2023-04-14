package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import models.dao.AdminDAO;
import models.dto.Admin;

public class AdminService {
	private AdminDAO dao = new AdminDAO();

	public Admin select(String username, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Admin dto = dao.selectByUsername(username);
		
		return dto != null && encoder.matches(password, dto.getPassword()) ? dto : null;
	}
}
