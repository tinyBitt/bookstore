package cn.baoxin.bookstore.order.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.baoxin.bookstore.user.domain.User;

public class Order {
	private String oid;
	private Date ordertime;
	private int state;//0未付款 1已付款未发货 2已发货未确认收货  3已确认收货
	private User user;
	private String adress;
	
	private List<OrderItem> orderItemList;
	
	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	
	public double getTotal() {
		BigDecimal total = new BigDecimal(0);
		for(OrderItem o : orderItemList){
			BigDecimal subtotal = new BigDecimal(o.getSubtotal());
			total = total.add(subtotal);
		}
		return total.doubleValue();
	}
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", state=" + state + ", user="
				+ user + ", adress=" + adress + "]";
	}
}
