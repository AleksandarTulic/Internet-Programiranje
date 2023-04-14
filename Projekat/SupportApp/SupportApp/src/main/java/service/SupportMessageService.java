package service;

import models.dao.SupportMessageDAO;

import java.util.List;
import models.dto.SupportMessage;
import validation.ValidationMessage;

public class SupportMessageService {
	private SupportMessageDAO dao = new SupportMessageDAO();
	
	public List<SupportMessage> select(byte read, String message, Long left, Long right){
		return dao.select(read, message, left, right);
	}
	
	public Long selectNumberOfMessages(byte read) {
		return dao.selectNumberOfMessages(read);
	}
	
	public boolean updateReturnMessage(Long id, Long supportSpecialistId, String return_message) {
		boolean flag = false;
		if (new ValidationMessage(return_message).check())
			flag = dao.updateReturnMessage(id, supportSpecialistId, return_message);
	
		return flag;
	}
	
	public boolean updateRead(Long id, Boolean flag_read) {
		return dao.updateRead(id, flag_read);
	}
}
