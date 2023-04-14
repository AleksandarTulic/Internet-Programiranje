package models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import logger.MyLogger;
import models.dto.Category;
import models.dto.CategoryField;

public class CategoryDAO {
	private static ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
	private static final String SQL_SELECT_1 = "select * from category as c limit ?, ?;";
	private static final String SQL_SELECT_2 = "select * from category as c inner join category_fields as cf on cf.category_title=c.title where c.title=?;";
	private static final String SQL_SELECT_NUMBER_OF_CATEGORIES = "select count(*) as numberOfCategories from category as c;";
	private static final String SQL_INSERT = "insert into category values(?);";
	private static final String SQL_INSERT_FIELD = "insert into category_fields values(?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE_1 = "update category as c set c.title=? where c.title=?;";
	private static final String SQL_UPDATE_2 = "update category_fields as c set c.name=? where c.category_title=? and c.name=?;";
	private static final String SQL_DELETE = "delete from category as c where c.title=?;";
	
	public boolean delete(String title) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {title};
		
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
	
	public List<Category> select(Long left, Long right) {
		List<Category> arr = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {left, right};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_1, false, values);
			rs = pre.executeQuery();

			while (rs.next()) {
				arr.add(new Category(rs.getString("title")));
			}
			
			for (Category i : arr) {
				values = new Object[] {i.getTitle()};
				pre = DAOUtil.prepareStatement(conn, SQL_SELECT_2, false, values);
				rs = pre.executeQuery();
				
				List<CategoryField> arrField = new ArrayList<>();
				while (rs.next()) {
					arrField.add(new CategoryField(rs.getString("name"), rs.getString("field_type"), rs.getString("regex"), rs.getBoolean("flag_fixed_multiple_values")));
				}
				
				i.setFields(arrField);
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return arr;
	}
	
	public Long selectNumberOfCategories() {
		Long res = 0L;
		Connection conn = null;
		ResultSet rs = null;
		Object []values = new Object[] {};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_SELECT_NUMBER_OF_CATEGORIES, false, values);
			rs = pre.executeQuery();

			if (rs.next()) {
				res = rs.getLong("numberOfCategories");
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}

		return res;
	}
	
	public boolean insert(Category c) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {c.getTitle()};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_INSERT, false, values);
			int result = pre.executeUpdate();

			flag = result == 1 ? true : false;
			
			for (CategoryField i : c.getFields()) {
				values = new Object[] {c.getTitle(), i.getName(), i.getFieldType(), i.getRegex(), i.getFlagFixedMultipleValues()};
				pre = DAOUtil.prepareStatement(conn, SQL_INSERT_FIELD, false, values);
				
				result = pre.executeUpdate();
				
				flag = flag && (result == 1 ? true : false);
			}
			
			pre.close();
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
	
	public boolean insertOnlyCategories(String title, List<CategoryField> arr) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = null;
			
			for (CategoryField i : arr) {
				values = new Object[] {title, i.getName(), i.getFieldType(), i.getRegex(), i.getFlagFixedMultipleValues()};
				pre = DAOUtil.prepareStatement(conn, SQL_INSERT_FIELD, false, values);
				int result = pre.executeUpdate();

				flag = flag && (result >= 1 ? true : false);
			}
			pre.close();
			
		}catch (Exception e) {
			MyLogger.logger.log(Level.SEVERE, e.getMessage());
		}finally {
			connectionPool.checkIn(conn);
		}
		
		return flag;
	}
	
	public boolean update(Category c, String oldTitle) {
		boolean flag = false;
		Connection conn = null;
		Object []values = new Object[] {c.getTitle(), oldTitle};
		
		try {
			conn = connectionPool.checkOut();
			PreparedStatement pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_1, false, values);
			int result = pre.executeUpdate();

			flag = result == 1 ? true : false;
			
			if (flag) {
				for (CategoryField i : c.getFields()) {
					values = new Object[] {i.getName().split("-")[0], c.getTitle(), i.getName().split("-")[1]};
					pre = DAOUtil.prepareStatement(conn, SQL_UPDATE_2, false, values);
					
					result = pre.executeUpdate();
					
					flag = flag && (result == 1 ? true : false);
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
}
