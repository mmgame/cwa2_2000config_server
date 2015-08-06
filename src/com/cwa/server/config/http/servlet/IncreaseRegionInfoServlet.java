package com.cwa.server.config.http.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serverice.config.NewRegionEvent;
import baseice.event.IEventListenerPrx;
import baseice.service.FunctionTypeEnum;

import com.cwa.component.data.DBService;
import com.cwa.component.data.IDBSession;
import com.cwa.component.functionmanage.IFunctionCluster;
import com.cwa.component.functionmanage.IFunctionService;
import com.cwa.data.entity.IRegionEntityDao;
import com.cwa.data.entity.domain.RegionEntity;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.service.constant.ServiceConstant;

/**
 * 添加区信息，区信息在每个组的0区里
 * 
 * @author mausmars
 *
 */
public class IncreaseRegionInfoServlet extends HttpServlet {
	protected static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	private static final long serialVersionUID = 1L;

	private IConfigContext context;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		int rid = Integer.parseInt(request.getParameter("rid"));
		String name = request.getParameter("name");
		int state = Integer.parseInt(request.getParameter("state"));
		int userState = Integer.parseInt(request.getParameter("userState"));

		// 添加区信息到数据库
		DBService dbService = (DBService) context.getGloabalContext().getService(gid, ServiceConstant.DatabaseKey);
		if (dbService == null) {
			return;
		}
		IDBSession dbSession = dbService.getDBSession(ServiceConstant.General_Rid);
		IRegionEntityDao entityDao = (IRegionEntityDao) dbSession.getEntityDao(RegionEntity.class);

		RegionEntity entity = new RegionEntity();
		entity.rid = rid;
		entity.name = name;
		entity.state = state;
		entity.useState = userState;
		entityDao.insertEntity(entity, dbSession.getParams(ServiceConstant.General_Rid));

		// 通知其他服务，发送事件
		NewRegionEvent event = new NewRegionEvent();
		event.gid = gid;
		event.rid = rid;

		notify(gid, FunctionTypeEnum.Account, event);
		notify(gid, FunctionTypeEnum.Foyer, event);
	}

	private void notify(int gid, FunctionTypeEnum ftype, NewRegionEvent event) {
		IFunctionService functionService = context.getGloabalContext().getFunctionService(gid);
		if (functionService == null) {
			return;
		}
		IFunctionCluster functionCluster = functionService.getFunctionCluster(gid, ftype);
		if (functionCluster == null) {
			return;
		}
		List<IEventListenerPrx> eventListenerPrxs = functionCluster.getAllService(IEventListenerPrx.class);

		for (IEventListenerPrx eventListenerPrx : eventListenerPrxs) {
			// 发送
			eventListenerPrx.answer(event);
		}
	}

	// -----------------------------------------
	public void setContext(IConfigContext context) {
		this.context = context;
	}
}
