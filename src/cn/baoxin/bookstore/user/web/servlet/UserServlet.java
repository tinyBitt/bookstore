package cn.baoxin.bookstore.user.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.cart.domain.Cart;
import cn.baoxin.bookstore.user.domain.UnactiveException;
import cn.baoxin.bookstore.user.domain.User;
import cn.baoxin.bookstore.user.domain.UserException;
import cn.baoxin.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * 用户模块的web层
 * @author lbxss
 *
 */
@WebServlet(urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	
	/**
	 * 用户退出
	 * @param req
	 * @param resp
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String exit(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		/*
		 * 获取请求携带的session，然后使之失效
		 * 转发到index.jsp
		 */
		req.getSession().invalidate();
		return "/index.jsp";
	}
	/**
	 * 用户登录
	 * 
	 * @param req
	 * @param resp
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1、一键封装表单数据 
		 * 2、用user调用service的login方法
		 * 3、若抛出UserException异常，保存错误信息，并转发到login.jsp 
		 * 4、否则保存成功信息，并转发到index.jsp
		 */
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		try {
			User _user = userService.login(user);
			//登录时，给登陆成功用户添加空购物车
			req.getSession().setAttribute("cart", new Cart());
			req.getSession().setAttribute("session_user", _user);
			return "f:/index.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("user", user);
			return "f:/jsps/user/login.jsp";
		} catch (UnactiveException e) {
			req.setAttribute("user", user);
			return "f:/ActivateServlet";
		}
	}

	/**
	 * 表单校验之校验邮箱
	 * 
	 * @param req
	 * @param resp
	 * @return 用于标志校验是否通过
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean validateEml(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1、获取请求参数email 
		 * 2、用email调用service的validateEml方法
		 * 3、如果抛出UserException，向页面输出异常信息
		 */
		try {
			String email = req.getParameter("email");
			userService.validateEml(email);
			return true;
		} catch (UserException e) {
			resp.getWriter().print(e.getMessage());
			return false;
		}
	}

	/**
	 * 表单校验之校验用户名
	 * 
	 * @param req
	 * @param resp
	 * @return 用于标志校验是否通过
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean validateUname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1、获取请求参数username 
		 * 2、用username调用service的validate方法
		 * 3、如果抛出UserException，向页面输出异常信息
		 */
		try {
			String username = req.getParameter("username");
			userService.validateUname(username);
			return true;
		} catch (UserException e) {
			resp.getWriter().print(e.getMessage());
			return false;
		}
	}

	/**
	 * 激活账号
	 * 
	 * @param req
	 * @param resp
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1、获取请求参数code 
		 * 2、用code调用service的activate方法
		 * 3、若抛出UserException异常，保存错误信息到request域，并转发到msg.jsp
		 * 4、保存user到request域（用于登录免输入），转发到login.jsp
		 */
		String code = req.getParameter("code");
		try {
			User user = userService.activate(code);
			req.setAttribute("user", user);
			return "f:/jsps/user/login.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			return "f:/jsps/msg.jsp";
		}
	}

	/**
	 * 注册用户
	 * 
	 * @param req
	 * @param resp
	 * @return 做转发操作
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 一键封装表单数据
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		// 补全uid、code和state
		user.setUid(CommonUtils.uuid());
		user.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		user.setState(false);
		/*
		 * 如果表单校验失败，保存user到request域，并转发到regist.jsp
		 */
		if (!(validateUname(req, resp) && validateEml(req, resp))) {
			req.setAttribute("user", user);
			req.setAttribute("msg", "请正确填写表单之后，再点击注册！");
			return "f:/jsps/user/regist.jsp";
		}
		/*
		 * 用user调用service的regist方法 
		 *   >保存user到request域，并转发到ActivateServlet
		 */
		try {
			userService.regist(user);
			req.setAttribute("user", user);
			return "f:/ActivateServlet";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("user", user);
			return "f:/jsps/user/regist.jsp";
		}
	}
}
