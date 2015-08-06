<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.UrlConstant" %>
<%
	String url=UrlConstant.getUrl(UrlConstant.context_increaseRegion);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="charset=UTF-8">
<title>添加区 </title>
<link rel="stylesheet" type="text/css" href="style/main.css" charset="utf-8"/>
</head>
<body>
<div id="box">
	<div id="title">添加区</div>
	<form method="get" action="<%=url%>">
		<table cellpadding="0" cellspacing="0" width="100%" id="increaseR_table">
			<tr>
				<td class="increaseR">组id：</td>
				<td class="increaseR_text"><input type="text" name="gid"/></td>
			</tr>
			<tr>
				<td class="increaseR">db的id：</td>
				<td class="increaseR_text"><input type="text" name="dbid"/></td>
			</tr>
			<tr>
				<td class="increaseR">区ids：</td>
				<td class="increaseR_text"><input type="text" name="rids"/></td>
			</tr>
		</table>
		<div id="submit"><input type="submit" id="submit_btn" value="提交"></div>
	</form>
</div>
</body>
</html>
