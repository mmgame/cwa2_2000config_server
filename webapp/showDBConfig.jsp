<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.*" %>
<%@ page language="java" import="com.cwa.data.config.domain.*" %>
<%@ page language="java" import="java.util.*" %>

<%
	Collection<DatabaseInfoConfig> configs=(Collection<DatabaseInfoConfig>)request.getAttribute(DatabaseInfoConfig.class.getSimpleName());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="../style/main.css" charset="utf-8"/>
<script type="text/javascript" src="../script/jquery.min.js"></script>
</head>
<body>
		<table cellpadding="0" cellspacing="0" width="80%" border="1" id="searchTable" align="center">
		<tr>
			<th>组id</td>
			<th>数据配置id</td>
			<th>ip地址</td>
			<th>端口</td>
			<th>库名</td>
			<th>用户名</td>
			<!-- <th>密码</td> -->
			<th>区ids</td>
			<th>mybatis配置路径</td>
		</tr>
		<% for(DatabaseInfoConfig dbic : configs){%>
			<tr>
				<td><%=dbic.gid %></td>
				<td><%=dbic.dbid %></td>
				<td><%=dbic.ip %></td>
				<td><%=dbic.port %></td>
				<td><%=dbic.dbName %></td>
				<td ><%=dbic.user %></td>
				<!--<td><%=dbic.password %></td>-->
				<td><%=dbic.regionMap %></td>
				<td><%=dbic.mybatis %></td>
			</tr>
		<% } %>
		</table>
</body>
</html>
