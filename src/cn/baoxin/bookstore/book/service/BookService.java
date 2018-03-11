package cn.baoxin.bookstore.book.service;

import java.util.List;

import cn.baoxin.bookstore.book.dao.BookDao;
import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.category.dao.CategoryDao;
import cn.baoxin.bookstore.category.domain.Category;
import cn.itcast.commons.CommonUtils;

/**
 * 图书模块的业务层
 * @author lbxss
 *
 */
public class BookService {
	private BookDao bookDao = new BookDao();
	
	/**
	 * 过渡操作: 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		return bookDao.findAll();
	}
	/**
	 * 过渡操作： 按分类查询图书
	 * @param cid
	 * @return
	 */
	public List<Book> findByCid(String cid) {
		return bookDao.findByCid(cid);
	}
	/**
	 * 过渡操作： 根据bid查询图书
	 * @param bid
	 * @return
	 */
	public Book findBookByBid(String bid) {
		Book book = CommonUtils.toBean(bookDao.findBookByBid(bid), Book.class);
		Category category = CommonUtils.toBean(bookDao.findBookByBid(bid), Category.class);
		book.setCategory(category);
		return book;
	}
	/**
	 * 根据给定图书修改图书
	 * @param book
	 */
	public void mod(Book book) {
		bookDao.mod(book);
	}
	/**
	 * 添加图书
	 * @param book
	 */
	public void add(Book book) {
		bookDao.add(book);
	}
	/**
	 * 根据bid删除图书
	 * @param bid
	 */
	public void del(String bid) {
		bookDao.del(bid);
	}
}
