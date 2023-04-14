package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import models.dto.Log;
import models.dto.User;
import logger.MyLogger;

public class LogDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT = "select * from ((select l.id, l.description, l.level, date(l.datetime) as onlyDate, time(l.datetime) onlyTime, u.first_name, u.last_name from log as l inner join user as u on l.consumer_id=u.id where u.first_name like ? or u.last_name like ? or l.level like ?)"
			+ "union"
			+ "(select l.id, l.description, l.level, date(l.datetime) as onlyDate, time(l.datetime) onlyTime, null first_name, null last_name from log as l where l.consumer_id is null and (l.datetime like ? or l.level like ?))) as tabela limit ?, ?;";
	//private static final String SQL_SELECT_NUMBER_OF_LOGS = "select count(*) as numberOfLogs from log as l inner join user as u on l.consumer_id=u.id where u.first_name like ? or u.last_name like ? or l.level like ?;";

	private static final String SQL_SELECT_NUMBER_OF_LOGS = "select count(*) as numberOfLogs from ("
			+ "(select l.id, l.description, l.level, date(l.datetime) as onlyDate, time(l.datetime) onlyTime, u.first_name, u.last_name from log as l inner join user as u on l.consumer_id=u.id where u.first_name like ? or u.last_name like ? or l.level like ?)"
			+ "union"
			+ "(select l.id, l.description, l.level, date(l.datetime) as onlyDate, time(l.datetime) onlyTime, null first_name, null last_name from log as l where l.consumer_id is null and (l.datetime like ? or l.level like ?))"
			+ ") as tabela;";

	
	public List<Log> select(String searchBy, Long left, Long right) {
		List<Log> arr = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {"%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%", left, right};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT, false, values);
			rs = pre.executeQuery();

			while (rs.next()) {
				arr.add(new Log(rs.getLong("id"), rs.getString("description"), rs.getString("level"), rs.getString("onlyDate"), rs.getString("onlyTime"), new User(rs.getString("first_name"), rs.getString("last_name"))));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return arr;
	}
	
	public Long selectNumberOfLogs(String searchBy) {
		Long num = 0L;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {"%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%", "%" + searchBy + "%"};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_NUMBER_OF_LOGS, false, values);
			rs = pre.executeQuery();

			if (rs.next()) {
				num = rs.getLong("numberOfLogs");
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return num;
	}
}
