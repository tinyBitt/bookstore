package cn.baoxin.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.book.service.BookService;
import cn.baoxin.bookstore.cart.domain.Cart;
import cn.baoxin.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

@WebServlet(urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 从session中取出cart，调用clearCart（）方法，转发到list.jsp
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clearCart();
		return "f:/jsps/cart/list.jsp";
	}
	/**
	 * 删除指定购物车条目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1、从session中取出购物车 
		 * 2、获取请求参数bid，用bid调用cart的removeCartItem方法，删除指定购物车条目
		 * 3、转发到list.jsp
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.removeCartItem(bid);
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * 添加购物车条目
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addCartItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.从session中取出cart
		 * 2.获取请求参数bid和count
		 * 3.用bid查询book，将其与count一起封装到cartItem中 
		 * 4.将cartItem添加到cart中 
		 * 5.转发到list.jsp
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		String bid = request.getParameter("bid");
		Book book = new BookService().findBookByBid(bid);
		
		String count = request.getParameter("count");
		
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(Integer.parseInt(count));
		
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}
}
