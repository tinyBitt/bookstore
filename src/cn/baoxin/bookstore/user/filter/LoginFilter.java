package cn.baoxin.bookstore.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.baoxin.bookstore.user.domain.User;

@WebFilter( urlPatterns={"/jsps/order/*", "/jsps/cart/*", "/OrderServlet", "/CartServlet"})
public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		User user = (User) req.getSession().getAttribute("session_user");
		if(user != null){
			chain.doFilter(request, response);
		}else{
			req.setAttribute("msg", "您还没登录！");
			req.getRequestDispatcher("/jsps/user/login.jsp").forward(req, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
