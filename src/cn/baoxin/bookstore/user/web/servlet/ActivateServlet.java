package cn.baoxin.bookstore.user.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.baoxin.bookstore.user.domain.User;
import cn.baoxin.bookstore.user.service.UserService;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;

/**
 *发送激活邮件的servlet 
 * @author lbxss
 *
 */
@WebServlet(urlPatterns="/ActivateServlet")
public class ActivateServlet extends HttpServlet {
	private UserService userService = new UserService();
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		//设置编码问题
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		//获取请求参数
		User user = (User) req.getAttribute("user");
		//用于重发激活邮件
		User _user = userService.findByUsername(user.getUsername());
		/*
		 * 发送激活邮件
		 * 	1、获取配置文件
		 * 	2、使用mail工具发送
		 */
		//获取properties文件
		Properties properties = new Properties();
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("Mail_properties.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		/*
		 * 1、获取session
		 * 2、格式化字符串（占位符）
		 * 3、获得mail邮件
		 * 4、发送
		 */
		Session session = MailUtils.createSession(properties.getProperty("host"), properties.getProperty("username"),
				properties.getProperty("password"));
		String content = properties.getProperty("content");
		content = MessageFormat.format(content, _user.getCode());
		Mail mail = new Mail(properties.getProperty("sender"), _user.getEmail(), properties
				.getProperty("subject"), content);
		try {
			MailUtils.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		req.setAttribute("msg", "账号尚未激活，请前往注册邮箱激活！");
		req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
	}
}
