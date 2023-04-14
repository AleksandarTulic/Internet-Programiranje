package service;

import models.dao.LogDAO;
import models.dto.Log;
import java.util.List;

public class LogService {
	private LogDAO dao = new LogDAO();
	
	public List<Log> select(String searchBy, Long left, Long right){
		return dao.select(searchBy, left, right);
	}
	
	public Long selectNumberOfLogs(String searchBy) {
		return dao.selectNumberOfLogs(searchBy);
	}
}
