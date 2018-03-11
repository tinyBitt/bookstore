package cn.baoxin.bookstore.order.domain;

import java.math.BigDecimal;

import cn.baoxin.bookstore.book.domain.Book;

public class OrderItem {
	private String iid;
	private int count;
	private Order order;
	private Book book;
	
	public double getSubtotal() {
		BigDecimal b1 = new BigDecimal(count);
		BigDecimal b2 = new BigDecimal(book.getPrice());
		return b1.multiply(b2).doubleValue();
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "OrderItem [iid=" + iid + ", count=" + count + ", order=" + order + ", book="
				+ book + "]";
	}
}
