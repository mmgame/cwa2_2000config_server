package com.cwa.server.config.http.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import serverice.config.NewRegionDBEvent;
import baseice.constant.SeparatorComma;
import baseice.event.IEventListenerPrx;
import baseice.service.FunctionTypeEnum;

import com.cwa.component.data.DBService;
import com.cwa.component.data.IDBSession;
import com.cwa.component.data.IEntityDao;
import com.cwa.component.functionmanage.IFunctionCluster;
import com.cwa.component.functionmanage.IFunctionService;
import com.cwa.data.config.domain.DatabaseInfoConfig;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.server.config.dataFunction.DatabaseInfoDataFunction;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.init.servicefactory.IDBServiceFactory;
import com.cwa.util.StringUtil;

/**
 * 添加数据库配置信息，添加区
 * 
 * @author mausmars
 *
 */
public class IncreaseRegionAndDBinfoServlet extends HttpServlet {
	protected static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	private static final long serialVersionUID = 1L;

	private IConfigContext context;
	private IDBServiceFactory serviceFactory;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		String ip = request.getParameter("ip");
		int port = Integer.parseInt(request.getParameter("port"));
		String dbName = request.getParameter("dbName");
		String user = request.getParameter("user");
		String password = request.getParameter("password");
		String mybatis = request.getParameter("mybatis");
		String rids = request.getParameter("rids");

		List<Integer> ridList = StringUtil.str2IntList(rids, SeparatorComma.value);

		DBService dbService = (DBService) context.getGloabalContext().getService(gid, ServiceConstant.DatabaseKey);
		if (dbService == null) {
			return;
		}

		DatabaseInfoDataFunction ddFunction = (DatabaseInfoDataFunction) context.getDataFunctionManager().getDataFunction(
				DatabaseInfoConfig.class);
		// 增加区
		DatabaseInfoConfig entity = ddFunction.createNewEntity(gid, ip, port, dbName, user, password, mybatis, ridList);
		if (entity == null) {
			return;
		}

		// 创建区的表
		IDBSession dbSession = null;
		try {
			dbSession = serviceFactory.createDBSession(dbService.getServiceConfig(), entity);
		} catch (Exception e) {
			logger.error("", e);
			return;
		}
		for (int rid : ridList) {
			dbService.insertDBSession(dbSession.getKey(), rid, dbSession);
			for (IEntityDao dao : dbSession.getAllEntityDao()) {
				dao.createTable(dbSession.getParams(rid));
			}
		}

		// 通知逻辑服务器
		NewRegionDBEvent event = new NewRegionDBEvent();
		event.gid = gid;
		event.dbid = entity.dbid;

		notify(gid, FunctionTypeEnum.Logic, event);
	}

	// 通知逻辑服
	private void notify(int gid, FunctionTypeEnum ftype, NewRegionDBEvent event) {
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

	public void setServiceFactory(IDBServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}
}
