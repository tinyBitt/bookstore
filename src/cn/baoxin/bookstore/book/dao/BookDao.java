package cn.baoxin.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import cn.baoxin.bookstore.book.domain.Book;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		try {
			String sql = "SELECT * FROM book WHERE del = ?";
			return qr.query(sql, new BeanListHandler<Book>(Book.class), 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按分类查询图书
	 * @param cid
	 * @return
	 */
	public List<Book> findByCid(String cid) {
		try {
			String sql = "SELECT * FROM book WHERE cid = ?";
			return qr.query(sql, new BeanListHandler<Book>(Book.class), cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按bid查询图书
	 * @param bid
	 * @return
	 */
	public Map findBookByBid(String bid) {
		try {
			String sql = "SELECT * FROM book, category WHERE book.cid = category.cid AND bid = ?";
			return qr.query(sql, new MapHandler(), bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 修改图书
	 * @param book
	 */
	public void mod(Book book) {
		try {
			String sql = "UPDATE book SET bname=?, price=?, author=?, cid=? WHERE bid=?";
			qr.update(sql, book.getBname(), book.getPrice(), book.getAuthor()
					, book.getCategory().getCid(), book.getBid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加图书
	 * @param book
	 */
	public void add(Book book) {
		try {
			String sql = "INSERT INTO book VALUES(?, ?, ?, ?, ?, ?, ?)";
			qr.update(sql, book.getBid(), book.getBname(), book.getPrice(), book.getAuthor()
					, book.getImage(), book.getCategory().getCid(), 0);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据bid删除图书
	 * @param bid
	 */
	public void del(String bid) {
		try {
			String sql = "UPDATE book SET del = ? WHERE bid = ?";
			qr.update(sql, 1, bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
