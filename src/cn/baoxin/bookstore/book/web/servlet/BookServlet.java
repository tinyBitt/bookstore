package cn.baoxin.bookstore.book.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;
/**
 * 图书模块的web层
 * @author lbxss
 *
 */
@WebServlet(urlPatterns="/BookServlet")
public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	
	/**
	 * 查询图书详细
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findBookByBid(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1.获取请求参数bid
		 * 2.用bid调用service的findBookByBid方法，将返回的book保存到request域，转发到desc.jsp
		 */
		String bid = request.getParameter("bid");
		Book book = bookService.findBookByBid(bid);
		request.setAttribute("book", book);
		return "f:/jsps/book/desc.jsp";
	}
	/**
	 * 按分类查询图书
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByCid(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1.获取请求参数cid
		 * 2.用cid调用service的findByCid方法，将返回的bookList保存到request域，转发到list.jsp
		 */
		String cid = request.getParameter("cid");
		List<Book> bookList = bookService.findByCid(cid);
		request.setAttribute("bookList", bookList);
		return "f:/jsps/book/list.jsp";
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
		return "f:/jsps/book/list.jsp";
	}
}
