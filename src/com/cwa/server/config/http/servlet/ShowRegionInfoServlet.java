package com.cwa.server.config.http.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import baseice.basedao.IEntity;

import com.cwa.component.data.IDBService;
import com.cwa.component.data.IDBSession;
import com.cwa.data.entity.IRegionEntityDao;
import com.cwa.data.entity.domain.RegionEntity;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.server.config.http.constant.UrlConstant;
import com.cwa.service.constant.ServiceConstant;

public class ShowRegionInfoServlet extends HttpServlet {
	protected static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	private static final long serialVersionUID = 1L;

	private IConfigContext context;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 添加区信息到数据库
		List<IDBService> dbServices = context.getGloabalContext().getAllDBService();

		Map<Integer, List<? extends IEntity>> entitysMap = new HashMap<Integer, List<? extends IEntity>>();
		for (IDBService dbService : dbServices) {
			IDBSession dbSession = dbService.getDBSession(ServiceConstant.General_Rid);
			if (dbSession == null || dbService.getGid() == ServiceConstant.General_Gid) {
				continue;
			}
			IRegionEntityDao entityDao = (IRegionEntityDao) dbSession.getEntityDao(RegionEntity.class);
			List<? extends IEntity> entitys = entityDao.selectAllEntity(dbSession.getParams(ServiceConstant.General_Rid));
			entitysMap.put(dbService.getGid(), entitys);
		}

		request.setAttribute(RegionEntity.class.getSimpleName(), entitysMap);

		ServletContext sc = this.getServletContext();
		response.setContentType("text/html; charset=utf-8");
		RequestDispatcher rd = sc.getRequestDispatcher(UrlConstant.jsp_showRegionInfo);
		rd.forward(request, response);
	}

	// ---------------------------------------------------
	public void setContext(IConfigContext context) {
		this.context = context;
	}
}
