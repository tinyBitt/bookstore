package cn.baoxin.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Cart {
	private HashMap<String, CartItem> map = new LinkedHashMap<String, CartItem>();

	/**
	 * 获取购物车内商品总金额
	 * 
	 * @return
	 */
	public double getTotal() {
		BigDecimal total = new BigDecimal("0");
		for (CartItem cartItem : map.values()) {
			BigDecimal subtotal = new BigDecimal(cartItem.getSubtotal() + "");
			total = total.add(subtotal);
		}
		return total.doubleValue();
	}

	/**
	 * 向购物车添加购物车条目
	 * 
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		String bid = cartItem.getBook().getBid();
		/*
		 * 1、判断购物车内是否已存在该条目 2、如果存在，从map中取出该条目，并设置数量为原数量+新增数量 3、如果不存在，存入map
		 */
		if (map.containsKey(bid)) {
			CartItem _cartItem = map.get(bid);
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
		} else {
			map.put(bid, cartItem);
		}
	}

	/**
	 * 删除指定条目
	 * 
	 * @param bid
	 */
	public void removeCartItem(String bid) {
		map.remove(bid);
	}

	/**
	 * 清空购物车
	 */
	public void clearCart() {
		map.clear();
	}

	/**
	 * 获取购物车中所有条目的集合
	 * 
	 * @return
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
}
