<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.UrlConstant" %>
<%@ page language="java" import="com.cwa.server.config.http.constant.GmConstant" %>

<%
	String info=(String)request.getAttribute(GmConstant.ErrorInfo_Key);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
 	<title>错误页面</title>
 </head>

<body>
	<h1>出现错误！页面10秒后自动跳转</h1><br>	
	<meta http-equiv=refresh content="10;url=login.jsp">	
	
	<h2>错误信息:</h2><br>	
	<h2><%=info%></h2><br>	
</body>

</html>