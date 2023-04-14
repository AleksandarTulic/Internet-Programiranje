package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import logger.MyLogger;
import models.dto.SupportSpecialist;

public class SupportSpecialistDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_BY_USERNAME = "select u.id, u.first_name, u.last_name, u.password from support_specialist as su inner join user as u on u.id=su.user_id where u.username=?;";
	
	public SupportSpecialist selectByUsername(String username) {
		SupportSpecialist dto = null;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {username};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_BY_USERNAME, false, values);
			rs = pre.executeQuery();

			if (rs.next()) {
				dto = new SupportSpecialist(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), username, rs.getString("password"));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return dto;
	}
}
