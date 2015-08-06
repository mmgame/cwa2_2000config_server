<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.UrlConstant" %>
<%
	String url=UrlConstant.getUrl(UrlConstant.context_increaseRegionAndDBinfo);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="charset=UTF-8">
<title>添加区和数据库配置 </title>
<link rel="stylesheet" type="text/css" href="style/main.css" charset="utf-8"/>
</head>
<body>
<div id="box">
	<div id="title">添加区和数据库配置</div>
	<form method="get" action="<%=url%>">
		<table cellpadding="0" cellspacing="0" width="100%" id="increaseRDB_table">
			<tr>
				<td class="increaseRDB">组id：</td>
				<td class="increaseRDB_text"><input type="text" name="gid"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">ip地址：</td>
				<td class="increaseRDB_text"><input type="text" name="ip"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">端口：</td>
				<td class="increaseRDB_text"><input type="text" name="port"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">数据库名：</td>
				<td class="increaseRDB_text"><input type="text" name="dbName"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">账号名：</td>
				<td class="increaseRDB_text"><input type="text" name="user"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">账号密码：</td>
				<td class="increaseRDB_text"><input type="password" name="password"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">mybatis配置路径：</td>
				<td class="increaseRDB_text"><input type="text" name="mybatis"/></td>
			</tr>
			<tr>
				<td class="increaseRDB">区ids：</td>
				<td class="increaseRDB_text"><input type="text" name="rids"/></td>
			</tr>
		</table>
		<div id="submit"><input type="submit" id="submit_btn" value="提交"></div>
	</form>
</div>
</body>
</html>
