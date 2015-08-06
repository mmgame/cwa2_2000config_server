<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.UrlConstant" %>
<%
	String url=UrlConstant.getUrl(UrlConstant.context_increaseRegionInfo);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="charset=UTF-8">
<title>添加区信息 </title>
</head>
<body>
<div id="box">
	<div id="title">添加区信息</div>
	<form method="get" action="<%=url%>">
		<table cellpadding="0" cellspacing="0" width="100%" id="increaseRI_table">
			<tr>
				<td class="increaseRI">组id：</td>
				<td class="increaseRI_text"><input type="text" name="gid"/></td>
			</tr>
			<tr>
				<td class="increaseRI">rid：</td>
				<td class="increaseRI_text"><input type="text" name="rid"/></td>
			</tr>
			<tr>
				<td class="increaseRI">区名字：</td>
				<td class="increaseRI_text"><input type="text" name="name"/></td>
			</tr>
			<tr>
				<td class="increaseRI">区状态：</td>
				<td class="increaseRI_text"><input type="text" name="state"/></td>
			</tr>
			<tr>
				<td class="increaseRI">使用状态：</td>
				<td class="increaseRI_text"><input type="text" name="userState"/></td>
			</tr>
		</table>
		<div id="submit"><input type="submit" id="submit_btn" value="提交"></div>
	</form>
</div>
</body>
</html>
