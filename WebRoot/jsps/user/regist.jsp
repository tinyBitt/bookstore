<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
  	window.onload = function(){
  		/*
  		 *表单校验之用户名校验
  		 */
  		var uname = document.getElementById("uname");
  		uname.onblur = function(){
  			var xmlHttp = new XMLHttpRequest();
  			xmlHttp.open("post", "<c:url value='/UserServlet'/>", true);
  			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  			xmlHttp.send("method=validateUname&username=" + uname.value);
  			xmlHttp.onreadystatechange = function(){
  				if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
  					var text = xmlHttp.responseText;
  					var pu = document.getElementById("pu");
  					pu.innerHTML = text;
  				}
  			}
  		}
  		/*
  		 *表单校验之邮箱校验
  		 */
  		var eml = document.getElementById("eml");
  		eml.onblur = function(){
  			var xmlHttp = new XMLHttpRequest();
  			xmlHttp.open("post", "<c:url value='/UserServlet'/>", true);
  			xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  			xmlHttp.send("method=validateEml&email=" + eml.value);
  			xmlHttp.onreadystatechange = function(){
  				if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
  					var text = xmlHttp.responseText;
  					var pe = document.getElementById("pe");
  					pe.innerHTML = text;
  				}
  			}
  		}
  	}
  </script>
  <body>
  <h1>注册</h1>
<p style="color: red; font-weight: 900">${msg }</p>
<form action="<c:url value='/UserServlet'/>" method="post" target="_parent">
	<input type="hidden" name="method" value="regist"/>
	用户名：<input id="uname" type="text" name="username" value="${user.username }"/><span id="pu" style="color: red;"></span><br/>
	密　码：<input id="psw" type="password" name="password"/><br/>
	邮　箱：<input id="eml" type="text" name="email" value="${user.email }"/><span id="pe" style="color: red;"></span><br/>
	<input type="submit" value="注册"/>
</form>
  </body>
</html>
