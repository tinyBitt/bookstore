package cn.baoxin.bookstore.order.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.baoxin.bookstore.order.domain.Order;
import cn.baoxin.bookstore.order.domain.OrderItem;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 添加订单
	 * @param order
	 */
	public void addOrder(Order order) {
		String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?)";
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal()
				, order.getState(), order.getUser().getUid(), null};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 添加订单条目
	 * @param orderItem
	 */
	public void addOrderItem(OrderItem orderItem) {
		String sql = "INSERT INTO orderitem VALUES(?, ?, ?, ?, ?)";
		Object[] params = {orderItem.getIid(), orderItem.getCount(), orderItem.getSubtotal()
				, orderItem.getOrder().getOid(), orderItem.getBook().getBid()};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据uid查询订单（未封装orderItem的空壳order）
	 * @param uid
	 * @return List<Order>
	 */
	public List<Order> findOrdersByUid(String uid) {
		try {
			String sql = "SELECT * FROM orders WHERE uid = ?";
			return qr.query(sql, new BeanListHandler<Order>(Order.class), uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 根据oid查询订单项
	 * @param oid
	 * @return
	 */
	public List<Map<String,Object>> findOrderItemsByOid(String oid) {
		try {
			String sql = "SELECT * FROM orderitem, book WHERE orderitem.bid = book.bid AND oid = ?";
			return qr.query(sql, new MapListHandler(), oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 按oid查询订单
	 * @param oid
	 * @return
	 */
	public Order findOrderByOid(String oid) {
		try {
			String sql = "SELECT * FROM orders WHERE oid = ?";
			return qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
