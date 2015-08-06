<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.*" %>
<%@ page language="java" import="com.cwa.data.entity.domain.*" %>
<%@ page language="java" import="baseice.basedao.*" %>
<%@ page language="java" import="java.util.*" %>
<%
	Map<Integer, List<? extends IEntity>> entitysMap = (Map<Integer, List<? extends IEntity>>)request.getAttribute(RegionEntity.class.getSimpleName());
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
			<th>区id</td>
			<th>区名字</td>
			<th>区状态</td>
			<th>区使用状态</td>
		</tr>
		<% for(Map.Entry<Integer,List<? extends IEntity>> entitys : entitysMap.entrySet()){%>
			<% for(IEntity e : entitys.getValue()){
				RegionEntity entity=(RegionEntity)e;
			%>
				<tr>
					<th><%=entitys.getKey() %></td>
					<th><%=entity.rid %></td>
					<th><%=entity.name %></td>
					<th><%=entity.state %></td>
					<th><%=entity.useState %></td>
				</tr>
			<% } %>
		<% } %>
		</table>
</body>
</html>
