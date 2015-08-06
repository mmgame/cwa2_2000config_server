package com.cwa.server.config.http.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.server.config.http.bean.Constant;
import com.cwa.server.config.http.bean.UserInfo;
import com.cwa.server.config.http.constant.UrlConstant;

/**
 * 行为过滤器
 * 
 * @author mausmars
 *
 */
public class ActionFilter implements Filter {
	protected static final Logger logger = LoggerFactory.getLogger(ActionFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		response.setContentType("text/html; charset=utf-8");

		String servletPath = req.getServletPath();
		String remoteAddr = request.getRemoteAddr();
		if (logger.isInfoEnabled())
			logger.info("remoteAddr=" + remoteAddr + " servletPath=" + servletPath);

		if (servletPath.equals("/")) {
			UserInfo userInfo = (UserInfo) req.getSession().getAttribute(Constant.UserInfo_Key);
			if (userInfo == null) {
				res.sendRedirect(UrlConstant.getUrl(UrlConstant.jsp_login));
			} else {
				res.sendRedirect(UrlConstant.getUrl(UrlConstant.jsp_main));
			}
			return;
		}

		if (!servletPath.equals(UrlConstant.jsp_login) && !servletPath.equals(UrlConstant.context_login)) {
			UserInfo userInfo = (UserInfo) req.getSession().getAttribute(Constant.UserInfo_Key);
			if (userInfo == null) {
				res.sendRedirect(UrlConstant.getUrl(UrlConstant.jsp_login));
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
