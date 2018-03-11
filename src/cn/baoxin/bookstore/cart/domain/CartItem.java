package cn.baoxin.bookstore.cart.domain;

import java.math.BigDecimal;

import cn.baoxin.bookstore.book.domain.Book;

public class CartItem {
	private Book book;
	private int count;
	
	public double getSubtotal() {
		BigDecimal b1 = new BigDecimal(book.getPrice() + "");
		BigDecimal b2 = new BigDecimal(count + "");
		return b1.multiply(b2).doubleValue();
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + "]";
	}
	
}
