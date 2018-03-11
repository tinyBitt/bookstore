package cn.baoxin.bookstore.user.service;

import cn.baoxin.bookstore.user.dao.UserDao;
import cn.baoxin.bookstore.user.domain.UnactiveException;
import cn.baoxin.bookstore.user.domain.User;
import cn.baoxin.bookstore.user.domain.UserException;
/**
 * 用户模块的业务层
 * @author lbxss
 *
 */
public class UserService {
	private UserDao userDao = new UserDao();
	
	/**
	 * 业务：用户注册
	 * @param user
	 * @throws UserException 
	 */
	public void regist(User user) throws UserException {
		/*
		 * 已经经过ajax进行异步校验，直接添加即可
		 */
		if(user.getPassword() == null | user.getPassword().trim().isEmpty()) throw new UserException("密码不能为空！");
		userDao.add(user);
	}
	/**
	 * 业务：激活账户
	 * @param code
	 * @return
	 * @throws UserException 
	 */
	public User activate(String code) throws UserException {
		/*
		 * 1、根据code查询到用户user
		 * 2、如果user为空，抛出UserException异常
		 * 3、将user的state设置为true
		 */
		User user = userDao.findByCode(code);
		if(user == null) throw new UserException("激活链接已失效，请重新注册！");
		userDao.activate(user.getUid());
		return user;
	}
	/**
	 * 业务层：表单校验之校验用户名
	 * @param username
	 * @throws UserException 
	 */
	public void validateUname(String username) throws UserException {
		/*
		 * 1、判断用户名是否为空，增加程序健壮性
		 * 2、正则校验用户名长度和格式
		 * 3、查询用户名是否已被注册
		 *   *以上三种错误都抛出UserException，否则什么都不做
		 */
		if(username == null) throw new UserException("用户名不能为空！");
		if(!username.matches("\\w{3,10}")) throw new UserException("用户名为英文字母，下划线或数字，长度在3~10之间");
		User user = userDao.findByUsername(username);
		if(user != null) throw new UserException("用户名已被注册！");
	}
	/**
	 * 业务层： 表单校验之校验邮箱
	 * @param email
	 * @throws UserException
	 */
	public void validateEml(String email) throws UserException {
		/*
		 * 1、判断email是否为空，增加程序健壮性
		 * 2、正则校验email长度和格式
		 * 3、查询email是否已被注册
		 *   *以上三种错误都抛出UserException，否则什么都不做
		 */
		if(email == null) throw new UserException("email不能为空!");
		if(!email.matches("\\w+@\\w+\\.\\w+")) throw new UserException("邮箱格式不正确！");
		User user = userDao.findByEmail(email);
		if(user != null) throw new UserException("邮箱已被注册！");
	}
	/**
	 * 业务：登录功能
	 * @param user
	 * @return
	 * @throws UserException 
	 * @throws UnactiveException 
	 */
	public User login(User user) throws UserException, UnactiveException {
		/*
		 * 1、判断参数user是否为null，防止空指针异常，增加程序健壮性
		 * 2、用user调用dao的findByUsername方法，若返回_user为null，则代表账户不存在，抛出异常
		 * 3、 都不为null时，校验密码是否正确，错误，抛出异常，正确，返回_user
		 */
		if(user == null) throw new UserException("登录出现故障，请稍后重新登录！");
		User _user = userDao.findByUsername(user.getUsername());
		if(_user == null) throw new UserException("该账户不存在！");
		if(!_user.getPassword().equals(user.getPassword())) throw new UserException("密码错误！");
		if(!_user.isState()) throw new UnactiveException();
		return _user;
	}
	/**
	 * 过渡方法
	 * @param username
	 * @return
	 */
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
}
