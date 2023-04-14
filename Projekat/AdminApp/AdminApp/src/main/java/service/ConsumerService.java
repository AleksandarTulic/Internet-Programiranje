package service;

import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import models.dao.ConsumerDAO;
import models.dto.Consumer;
import validation.ValidationConsumer;

public class ConsumerService {
	private ConsumerDAO dao = new ConsumerDAO();
	private EmailService emailService = new EmailService();
	private BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
	
	private static final Integer MAX_TOKEN_VALUE = 9999;
	private static final Integer MIN_TOKEN_VALUE = 1000;
	
	public boolean delete(Long id) {
		return dao.delete(id);
	}
	
	public boolean insert(Consumer c) {
		boolean flag = false;
		if (new ValidationConsumer(c).check()) {
			c.setPassword(enc.encode(c.getPassword()));
			Integer token = new Random().nextInt(MAX_TOKEN_VALUE - MIN_TOKEN_VALUE + 1) + MIN_TOKEN_VALUE;
			flag = dao.insert(c, token);
			
			if (flag) {
				emailService.send(c, token + "");
			}
		}
		
		return flag;
	}
	
	public boolean update(Consumer c) {
		boolean flag = false;
		if (new ValidationConsumer(c).check()) {
			c.setPassword(enc.encode(c.getPassword()));
			flag = dao.update(c);
		}
		
		return flag;
	}
	
	public List<Consumer> select(String firstNameOrLastName, Long left, Long right){
		return dao.select(firstNameOrLastName, left, right);
	}
	
	public Long selectNumberOfUsers(String firstNameOrLastName) {
		return dao.selectNumberOfUsers(firstNameOrLastName);
	}
}
