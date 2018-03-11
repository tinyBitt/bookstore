package cn.baoxin.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.baoxin.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		try {
			String sql = "SELECT * FROM category";
			return qr.query(sql, new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加分类
	 * @param category
	 */
	public void add(Category category) {
		try {
			String sql = "INSERT INTO category VALUES(?, ?)";
			qr.update(sql, category.getCid(), category.getCname());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据cid查血分类
	 * @param cid
	 * @return
	 */
	public Category findByCid(String cid) {
		try {
			String sql = "SELECT * FROM category WHERE cid = ?";
			return qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 根据cid修改分类名
	 */
	public void update(Category category) {
		try {
			String sql = "UPDATE category SET cname = ? WHERE cid = ?";
			qr.update(sql, category.getCname(),category.getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据cid删除分类
	 * @param cid
	 */
	public void delete(String cid) {
		try {
			String sql = "DELETE FROM category WHERE cid = ?";
			qr.update(sql, cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
