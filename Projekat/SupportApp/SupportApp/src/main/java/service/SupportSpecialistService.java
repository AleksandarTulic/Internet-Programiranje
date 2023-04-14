package service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import models.dao.SupportSpecialistDAO;
import models.dto.SupportSpecialist;

public class SupportSpecialistService {
	private SupportSpecialistDAO dao = new SupportSpecialistDAO();
	
	public SupportSpecialist select(String username, String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		SupportSpecialist dto = dao.selectByUsername(username);
		
		return dto != null && encoder.matches(password, dto.getPassword()) ? dto : null;
	}
}
