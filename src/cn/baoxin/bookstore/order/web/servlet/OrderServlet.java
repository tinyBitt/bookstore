package cn.baoxin.bookstore.order.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.cart.domain.Cart;
import cn.baoxin.bookstore.cart.domain.CartItem;
import cn.baoxin.bookstore.order.domain.Order;
import cn.baoxin.bookstore.order.domain.OrderItem;
import cn.baoxin.bookstore.order.service.OrderService;
import cn.baoxin.bookstore.order.service.TransactionException;
import cn.baoxin.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet(urlPatterns="/OrderServlet")
public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();
	
	/**
	 * 按id查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、获取请求参数oid
		 * 2、用oid调用orderService的findOrderByOid
		 * 3、将返回的order保存到request域，转发到desc.jsp
		 */
		String oid = request.getParameter("oid");
		Order order = orderService.findOrderByOid(oid);
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
	}
	/**
	 * 我的订单 ：按用户查询订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、从session中取出user
		 * 2、用user调用orderService的findOrderByUser方法
		 * 3、将返回的orderList保存到request域，转发到list.jsp
		 */
		User user = (User) request.getSession().getAttribute("session_user");
		List<Order> orderList = orderService.findOrdersByUser(user);
		request.setAttribute("orderList", orderList);
		return "f:/jsps/order/list.jsp";
	}
	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String generateOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、从session中取出购物车
		 * 2、创建order对象、orderItem对象，组装，并将orderItem封装到order中
		 * 3、用order调用service的saveOrder方法
		 * 4、若抛出异常，保存错误信息，转发到msg.jsp
		 * 5、若不抛出异常，清空购物车，并将order保存到request域，转发到desc.jsp
		 */
		//从session中取出购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Order order = new Order();
		//获取购物车中所有购物车条目
		Collection<CartItem> cartItemList = cart.getCartItems();
		List<OrderItem> orderItemList = new LinkedList<OrderItem>();
		//遍历购物车条目,每一条目生成一个订单条目
		for(CartItem c : cartItemList){
			//封装数据到orderItem
			OrderItem orderItem = new OrderItem();
			orderItem.setIid(CommonUtils.uuid());
			orderItem.setCount(c.getCount());
			orderItem.setOrder(order);
			orderItem.setBook(c.getBook());
			//将此订单条目添加到集合中
			orderItemList.add(orderItem);
		}
		//封装数据到order
		order.setOrderItemList(orderItemList);
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());
		order.setState(0);
		order.setUser((User) request.getSession().getAttribute("session_user"));
		//用order调用service的saveOrder方法
		try {
			orderService.saveOrder(order);
			//订单生成成功，清空购物车
			cart.clearCart();
			request.setAttribute("order", order);
			return "f:/jsps/order/desc.jsp";
		} catch (TransactionException e) {
			request.setAttribute("msg", e.getMessage());
			return "f:/jsps/cart/list.jsp";
		}
	}
}
