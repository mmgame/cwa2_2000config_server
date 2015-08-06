<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java"
	import="com.cwa.server.config.http.constant.UrlConstant"%>
<%
	String url = UrlConstant.getUrl(UrlConstant.context_login);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="charset=UTF-8">
<title>魔兽大陆配置管理系统</title>
<link rel="stylesheet" type="text/css" href="style/main.css"
	charset="utf-8" />
</head>
<body>
	<div id="box">
		<div id="title">魔兽大陆配置管理系统</div>
		<form method="get" action="<%=url%>">
			<table cellpadding="0" cellspacing="0" width="100%" id="login_table">
				<tr>
					<td class="login">管理员账号：</td>
					<td class="login_text"><input type="text" name="name" /></td>
				</tr>
				<tr>
					<td class="login">密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
					<td class="login_text"><input type="password" name="password" /></td>
				</tr>
			</table>
			<div id="submit">
				<input type="submit" id="submit_btn" value="登录">
			</div>
		</form>
	</div>
</body>
</html>
