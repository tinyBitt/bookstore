package cn.baoxin.bookstore.category.test;

import java.util.List;

import org.junit.Test;

import cn.baoxin.bookstore.category.dao.CategoryDao;
import cn.baoxin.bookstore.category.domain.Category;

public class CategoryDaoTest {
	CategoryDao dao = new CategoryDao();
	
	@Test
	public void fun1(){
		List<Category> findAll = dao.findAll();
		for(Category c : findAll){
			System.out.println(c);
		}
	}
}
