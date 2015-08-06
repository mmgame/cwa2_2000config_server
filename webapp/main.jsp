<%@ page contentType="text/html;charset=utf-8"%>
<%@ page language="java" import="com.cwa.server.config.http.constant.UrlConstant" %>

<%
	String increaseRegion_url = UrlConstant.getUrl(UrlConstant.jsp_increaseRegion);
	String increaseRegionAndDBinfo_url = UrlConstant.getUrl(UrlConstant.jsp_increaseRegionAndDBinfo);
	String increaseRegionInfo_url = UrlConstant.getUrl(UrlConstant.jsp_increaseRegionInfo);
	String showDBConfig_url = UrlConstant.getUrl(UrlConstant.context_showDBConfig);
	String showRegionInfo_url = UrlConstant.getUrl(UrlConstant.context_showRegionInfo);
%>

<html>
<body>
<b>配置服功能</b><br/>
<a href="<%=increaseRegion_url%>" target="_blank" >添加新区</a><br/>
<a href="<%=increaseRegionAndDBinfo_url%>" target="_blank" >添加新库并添加新区</a><br/>
<a href="<%=increaseRegionInfo_url%>" target="_blank" >添加新区信息</a><br/>
<a href="<%=showDBConfig_url%>" target="_blank" >查看数据库配置</a><br/>
<a href="<%=showRegionInfo_url%>" target="_blank" >查看区信息</a><br/>
</body>
</html>