package com.cwa.server.config.http.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.server.config.http.bean.Constant;
import com.cwa.server.config.http.bean.UserInfo;
import com.cwa.server.config.http.constant.UrlConstant;
import com.cwa.server.config.manager.PasswordManager;

/**
 * 登陆servlet
 * 
 * @author mausmars
 *
 */
public class LoginServlet extends HttpServlet {
	protected static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	private PasswordManager passwordManager;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");

		if (name == null || password == null) {
			response.sendRedirect(UrlConstant.getUrl(UrlConstant.jsp_error));
			return;
		}

		HttpSession session = request.getSession();
		UserInfo userInfo = (UserInfo) session.getAttribute(Constant.UserInfo_Key);
		if (userInfo != null && userInfo.getUserName().equals(name) && passwordManager.isPassword(name, password)) {
			logger.info("name:" + name + " 刷新");
			response.setContentType("text/html; charset=utf-8");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(UrlConstant.jsp_main);
			rd.forward(request, response);
			return;
		}

		if (passwordManager.isPassword(name, password)) {
			userInfo = new UserInfo();
			userInfo.setUserName(name);
			session.setAttribute(Constant.UserInfo_Key, userInfo);

			response.setContentType("text/html; charset=utf-8");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(UrlConstant.jsp_main);
			rd.forward(request, response);
			return;
		}
		response.sendRedirect(UrlConstant.getUrl(UrlConstant.jsp_error));
	}

	// --------------------------------------------------
	public void setPasswordManager(PasswordManager passwordManager) {
		this.passwordManager = passwordManager;
	}
}