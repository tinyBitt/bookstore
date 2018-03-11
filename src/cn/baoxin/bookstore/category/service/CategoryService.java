package cn.baoxin.bookstore.category.service;

import java.util.List;

import cn.baoxin.bookstore.category.dao.CategoryDao;
import cn.baoxin.bookstore.category.domain.Category;
/**
 * 分类模块的业务层
 * @author lbxss
 * 
 */
public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	
	/**
	 * 业务层：查询所有分类
	 * @return
	 */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}
	/**
	 * 添加分类
	 * @param category
	 */
	public void add(Category category) {
		categoryDao.add(category);
	}
	/**
	 * 根据cid查询分类
	 * @param cid
	 * @return
	 */
	public Category findByCid(String cid) {
		return categoryDao.findByCid(cid);
	}
	/**
	 * 根据cid修改分类名
	 * @param category
	 */
	public void update(Category category) {
		categoryDao.update(category);
	}
	public void delete(String cid) {
		categoryDao.delete(cid);
	}
}
