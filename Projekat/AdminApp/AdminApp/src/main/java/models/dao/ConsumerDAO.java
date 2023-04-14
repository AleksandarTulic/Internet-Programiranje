package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.sql.Timestamp;
import logger.MyLogger;
import models.dto.Consumer;

public class ConsumerDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_INSERT_1 = "insert into user(first_name, last_name, username, password) values(?, ?, ?, ?);";
	private static final String SQL_INSERT_2 = "insert into consumer(user_id, email, phone, city, avatar) values(?, ?, ?, ?, ?);";
	private static final String SQL_INSERT_3 = "insert into confirmation_token values(?, ?, ?, ?, ?)";
	private static final String SQL_DELETE = "delete from user as u where u.id=?;";
	private static final String SQL_UPDATE_1 = "update user as u set u.first_name=?, u.last_name=?, u.username=?, u.password=? where u.id=?;";
	private static final String SQL_UPDATE_2 = "update consumer as c set c.email=?, c.phone=?, c.city=?, c.avatar=? where c.user_id=?;";
	private static final String SQL_SELECT = "select * from user as u inner join consumer as c on c.user_id=u.id where u.first_name like ? or u.last_name like ? limit ?, ?;";
	private static final String SQL_SELECT_NUMBER_OF_USERS = "select count(c.user_id) as numberOfUsers from consumer as c inner join user as u on u.id=c.user_id where u.first_name like ? or u.last_name like ?;";
	
	public boolean insert(Consumer c, Integer token) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {c.getFirstName(), c.getLastName(), c.getUsername(), c.getPassword()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INSERT_1, true, values);
			int result = pre.executeUpdate();
			
			flag = result == 1 ? true : false;
			
			if (flag) {
				Long id = -1L;
				ResultSet rs = pre.getGeneratedKeys();
				
				if (rs.next())
					id = rs.getLong(1);
				
				if (id != -1L) {
					values = new Object[] {id, c.getEmail(), c.getPhone(), c.getCity(), "".equals(c.getAvatar()) ? null : c.getAvatar()};
					pre = DAOUtil.prepareStatement(conn, SQL_INSERT_2, false, values);
					result = pre.executeUpdate();
					
					flag = result == 1 ? true : false;
					if (flag) {
						values = new Object[] {id, null, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(15)), token};
						pre = DAOUtil.prepareStatement(conn, SQL_INSERT_3, false, values);
						result = pre.executeUpdate();
						
						flag = result == 1 ? true : false;
					}
				}
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
	
	public boolean delete(Long id) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {id};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_DELETE, false, values);
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
	
	public boolean update(Consumer c) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {c.getFirstName(), c.getLastName(), c.getUsername(), c.getPassword(), c.getId()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_1, true, values);
			int result = pre.executeUpdate();
			
			flag = result == 1 ? true : false;
			
			if (flag) {
				values = new Object[] {c.getEmail(), c.getPhone(), c.getCity(), "".equals(c.getAvatar()) ? null : c.getAvatar(), c.getId()};
				pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_2, true, values);
				
				result = pre.executeUpdate();
				
				flag = result == 1 ? true : false;
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
	
	public List<Consumer> select(String firstNameOrLastName, Long left, Long right) {
		List<Consumer> arr = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {"%" + firstNameOrLastName + "%", "%" + firstNameOrLastName + "%", left, right};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT, false, values);
			rs = pre.executeQuery();

			while (rs.next()) {
				arr.add(new Consumer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("username"), rs.getString("password"), rs.getString("email"),
						rs.getString("phone"), rs.getString("city"), rs.getString("avatar")));
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return arr;
	}
	
	public Long selectNumberOfUsers(String firstNameOrLastName) {
		Long num = 0L;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {"%" + firstNameOrLastName + "%", "%" + firstNameOrLastName + "%"};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_NUMBER_OF_USERS, false, values);
			rs = pre.executeQuery();

			if (rs.next()) {
				num = rs.getLong("numberOfUsers");
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
