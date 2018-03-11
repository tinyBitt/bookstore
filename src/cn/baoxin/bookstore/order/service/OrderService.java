package cn.baoxin.bookstore.order.service;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.baoxin.bookstore.book.dao.BookDao;
import cn.baoxin.bookstore.book.domain.Book;
import cn.baoxin.bookstore.order.dao.OrderDao;
import cn.baoxin.bookstore.order.domain.Order;
import cn.baoxin.bookstore.order.domain.OrderItem;
import cn.baoxin.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	/**
	 * 保存订单
	 * @param order
	 * @throws TransactionException 
	 */
	public void saveOrder(Order order) throws TransactionException {
		try {
			JdbcUtils.beginTransaction();
			save(order);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
				throw new TransactionException("哎呀，订单生成异常，请稍后再试！");
			} catch (SQLException e1) {
				throw new RuntimeException();
			}
		}
	}
	/**
	 * 内部调用方法
	 * @param order
	 * @see private
	 */
	public void save(Order order) {
		orderDao.addOrder(order);
		for(OrderItem orderItem : order.getOrderItemList()){
			orderDao.addOrderItem(orderItem);
		}
	}
	/**
	 * 根据用户查询订单（已封装订单项）
	 * @param user
	 * @return
	 */                                                    
	public List<Order> findOrdersByUser(User user) {
		//根据uid查询order（未封装数据）
		List<Order> orderList = orderDao.findOrdersByUid(user.getUid());
		for(Order order : orderList){
			order.setUser(user);
			loadOrderItem(order);
		}
		return orderList;
	}
	/**
	 * 为空壳订单加载订单项
	 * @param order
	 */
	public void loadOrderItem(Order order) {
		if(order != null){
			List<Map<String, Object>> mapList = orderDao.findOrderItemsByOid(order.getOid());
			List<OrderItem> orderItemList = new LinkedList<OrderItem>();
			for(Map<String, Object> map : mapList){
				OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
				Book book = CommonUtils.toBean(map, Book.class);
				orderItem.setBook(book);
				orderItem.setOrder(order);
				orderItemList.add(orderItem);
			}
			order.setOrderItemList(orderItemList);
		}
		
	}
	/**
	 * 根据id查询订单
	 * @param oid
	 * @return
	 */
	public Order findOrderByOid(String oid) {
		Order order = orderDao.findOrderByOid(oid);
		loadOrderItem(order);
		return order;
	}
}
