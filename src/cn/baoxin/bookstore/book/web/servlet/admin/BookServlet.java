package cn.baoxin.bookstore.book.web.servlet.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.book.service.BookService;
import cn.baoxin.bookstore.category.domain.Category;
import cn.baoxin.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet(urlPatterns="/AdminBookServlet")
public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	
	/**
	 * 删除图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String del(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		bookService.del(bid);
		return findAll(request, response);
	}
	/**
	 * 添加图书前
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addPre(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、查询所有分类，保存到request域，转发到add.jsp
		 */
		List<Category> categoryList = new CategoryService().findAll();
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/book/add.jsp";
	}
	/**
	 * 修改图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String mod(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、获取请求参数
		 * 2、封装book，查询category，也封装到book
		 * 3、调用service的mod方法
		 * 4、调用findAll方法
		 */
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		Category category = new CategoryService().findByCid(request.getParameter("cid"));
		book.setCategory(category);
		bookService.mod(book);
		return findAll(request, response);
	}
	/**
	 * 查询图书详细
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String descBook(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1.获取请求参数bid
		 * 2.用bid调用service的findBookByBid方法，将返回的book保存到request域，转发到desc.jsp
		 */
		String bid = request.getParameter("bid");
		Book book = bookService.findBookByBid(bid);
		List<Category> categoryList = new CategoryService().findAll();
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("book", book);
		return "f:/adminjsps/admin/book/desc.jsp";
	}
	/**
	 * 查询所有图书
	 * @param request
	 * @param response
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 *调用service的findAll方法，将返回的bookList保存到request域，转发到list.jsp 
		 */
		List<Book> bookList = bookService.findAll();
		request.setAttribute("bookList", bookList);
		return "f:/adminjsps/admin/book/list.jsp";
	}
}
