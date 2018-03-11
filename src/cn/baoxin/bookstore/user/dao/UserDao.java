package cn.baoxin.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.baoxin.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		try{
		String sql = "SELECT * FROM tb_user WHERE username = ?";
		return qr.query(sql, new BeanHandler<User>(User.class),username);
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加User到数据库
	 * @param user
	 */
	public void add(User user) {
		try {
			String sql = "INSERT INTO tb_user VALUES(?, ?, ?, ?, ?, ?)";
			Object[] params = {user.getUid(), user.getUsername(), user.getPassword()
					, user.getEmail(), user.getCode(), user.isState()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据code查询用户
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		try {
			String sql = "SELECT * FROM tb_user WHERE code = ?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void activate(String uid) {
		String sql = "UPDATE tb_user SET state = true WHERE uid = ?";
		try {
			qr.update(sql, uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据email查询用户
	 * @param email
	 * @return
	 */
	public User findByEmail(String email) {
		try {
			String sql = "SELECT * FROM tb_user WHERE email = ?";
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
