package cn.baoxin.bookstore.category.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.category.domain.Category;
import cn.baoxin.bookstore.category.service.CategoryService;
import cn.itcast.servlet.BaseServlet;
/**
 * 分类模块的web层
 * @author lbxss
 *
 */
@WebServlet(urlPatterns="/CategoryServlet")
public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	/**
	 * 查询所有分类 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 调用service的findAll方法，并将返回的categoryList保存到request域，转发到left.jsp
		 */
		List<Category> categoryList = categoryService.findAll();
		request.setAttribute("categoryList", categoryList);
		return "/jsps/left.jsp";
	}
}
