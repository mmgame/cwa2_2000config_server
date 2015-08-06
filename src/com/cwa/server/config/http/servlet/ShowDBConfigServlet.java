package com.cwa.server.config.http.servlet;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.data.config.domain.DatabaseInfoConfig;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.server.config.dataFunction.DatabaseInfoDataFunction;
import com.cwa.server.config.http.constant.UrlConstant;

public class ShowDBConfigServlet extends HttpServlet {
	protected static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	private static final long serialVersionUID = 1L;

	private IConfigContext context;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DatabaseInfoDataFunction ddFunction = (DatabaseInfoDataFunction) context.getDataFunctionManager().getDataFunction(
				DatabaseInfoConfig.class);

		Collection<DatabaseInfoConfig> configs = ddFunction.getDatabaseInfoConfigs();

		request.setAttribute(DatabaseInfoConfig.class.getSimpleName(), configs);

		ServletContext sc = this.getServletContext();
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = sc.getRequestDispatcher(UrlConstant.jsp_showDBConfig);
		rd.forward(request, response);
	}

	public void setContext(IConfigContext context) {
		this.context = context;
	}
}
