package cn.baoxin.bookstore.category.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.category.domain.Category;
import cn.baoxin.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

@WebServlet(urlPatterns="/AdminCategoryServlet")
public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 删除分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		categoryService.delete(cid);
		return findAll(request, response);
	}
	/**
	 * 修改分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.update(category);
		return findAll(request, response);
	}
	/**
	 * 修改前
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editPre(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、从页面获取参数cid，查询category，保存到request域，转发到mod.jsp
		 */
		String cid = request.getParameter("cid");
		Category category = categoryService.findByCid(cid);
		request.setAttribute("category", category);
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、一键封装表单数据，补全cid
		 * 2、调用service的add方法
		 * 3、调用this.findAll方法
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.add(category);
		return findAll(request, response);
	}
	/**
	 * 查询所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		/*
		 * 1、调用service的findAll方法
		 * 2、将返回的categoryList保存到request域，转发到list.jsp
		 */
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/category/list.jsp";
	}
}
