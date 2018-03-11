package cn.baoxin.bookstore.user.test;

import org.junit.Test;

import cn.baoxin.bookstore.user.dao.UserDao;
import cn.baoxin.bookstore.user.domain.User;

public class UserDaoTest {
	UserDao dao = new UserDao();
	@Test
	public void fun1(){
		UserDao dao = new UserDao();
		User user = new User();
		user.setUid("111122");
		user.setUsername("zhangSan");
		user.setPassword("123");
		user.setEmail("zhangSan@163.com");
		user.setCode("fjiehtshdgufewjoge");
		user.setState(false);
		dao.add(user);
	}
	@Test
	public void fun2(){
		UserDao dao = new UserDao();
		User user = dao.findByUsername("zhang");
		System.out.println(user);
	}
	@Test
	public void fun3(){
		UserDao dao  = new UserDao();
		User user = dao.findByCode("361F24410AB741A89E5E78135AD577477ED001BFF76F4E2BBBA10B8788F5AF22");
		System.out.println(user);
	}
	@Test
	public void fun4(){
		dao.activate("111122");
	}
	@Test
	public void fun5(){
		User user = dao.findByEmail("1622814328@qq.com");
		System.out.println(user);
	}
}
