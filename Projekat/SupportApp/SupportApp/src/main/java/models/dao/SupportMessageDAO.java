package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import logger.MyLogger;
import models.dto.Consumer;
import models.dto.SupportMessage;

public class SupportMessageDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_ALL = "select sm.id, sm.title, sm.message, sm.datetime, sm.flag_read, sm.return_message, c.email, us.first_name, us.last_name from support_message as sm inner join consumer as c on c.user_id=sm.consumer_id inner join user as us on us.id=sm.consumer_id where sm.message like ? limit ?, ?";
	private static final String SQL_SELECT_READ_OR_NOT = "select sm.id, sm.title, sm.message, sm.datetime, sm.flag_read, sm.return_message, c.email, us.first_name, us.last_name from support_message as sm inner join consumer as c on c.user_id=sm.consumer_id inner join user as us on us.id=sm.consumer_id where sm.flag_read=? and sm.message like ? limit ?, ?";
	private static final String SQL_UPDATE_RETURN_MESSAGE = "update support_message as sm set sm.return_message=?, sm.support_specialist_id=? where sm.id=?";
	private static final String SQL_UPDATE_READ = "update support_message as sm set sm.flag_read=? where sm.id=?";
	private static final String SQL_SELECT_NUMBER_OF_MESSAGES_VERSION_1 = "select count(sm.message) as numberOfMessages from support_message as sm where sm.flag_read=?";
	private static final String SQL_SELECT_NUMBER_OF_MESSAGES_VERSION_2 = "select count(sm.message) as numberOfMessages from support_message as sm";
	
	public List<SupportMessage> select(byte read, String message, Long left, Long right){
		List<SupportMessage> arr = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;
		message = "%" + message + "%";
		Object []values = new Object[] {message, left, right};
		
		if (read == 0 || read == 1)
			values = new Object[] {read, message, left, right};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, (read == 0 || read == 1) ? SQL_SELECT_READ_OR_NOT : SQL_SELECT_ALL, false, values);
			rs = pre.executeQuery();

			while (rs.next()) {
				arr.add(new SupportMessage(rs.getLong("id"), rs.getString("title"), rs.getString("message"), rs.getString("datetime"), rs.getBoolean("flag_read")
						, rs.getString("return_message"), new Consumer(rs.getString("email"), rs.getString("first_name"), rs.getString("last_name"))));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return arr;
	}
	
	public Long selectNumberOfMessages(byte read){
		Long num = 0L;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {};
		if (read == 0 || read == 1)
			values = new Object[] {read};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, read == 0 || read == 1 ? SQL_SELECT_NUMBER_OF_MESSAGES_VERSION_1 : SQL_SELECT_NUMBER_OF_MESSAGES_VERSION_2, false, values);
			rs = pre.executeQuery();

			if (rs.next()) {
				num = rs.getLong("numberOfMessages");
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return num;
	}
	
	public boolean updateReturnMessage(Long id, Long supportSpecialistId, String return_message) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {return_message, supportSpecialistId, id};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_RETURN_MESSAGE, false, values);
			int result = pre.executeUpdate();
			
			flag = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
	
	public boolean updateRead(Long id, Boolean flag_read) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {flag_read, id};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_READ, false, values);
			int result = pre.executeUpdate();
			
			flag = result == 1 ? true : false;
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
}
