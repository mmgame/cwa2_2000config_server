package com.cwa.server.config.http.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.cwa.util.StringUtil;

/**
 * 添加新区
 * 
 * @author mausmars
 *
 */
public class IncreaseRegionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IConfigContext context;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int gid = Integer.parseInt(request.getParameter("gid"));
		int dbid = Integer.parseInt(request.getParameter("dbid"));
		String rids = request.getParameter("rids");

		List<Integer> ridList = StringUtil.str2IntList(rids, SeparatorComma.value);

		// 创建表
		boolean isSuccess = createTable(gid, dbid, ridList);
		if (!isSuccess) {
			return;
		}

		// 增加区
		DatabaseInfoDataFunction ddFunction = (DatabaseInfoDataFunction) context.getDataFunctionManager().getDataFunction(
				DatabaseInfoConfig.class);
		isSuccess = ddFunction.increaseRegion(gid, dbid, ridList);
		if (!isSuccess) {
			return;
		}

		// 通知逻辑服务器
		NewRegionDBEvent event = new NewRegionDBEvent();
		event.gid = gid;
		event.dbid = dbid;

		notify(gid, FunctionTypeEnum.Logic, event);
	}

	private boolean createTable(int gid, int dbid, List<Integer> ridList) {
		DBService dbService = (DBService) context.getGloabalContext().getService(gid, ServiceConstant.DatabaseKey);
		if (dbService == null) {
			return false;
		}
		IDBSession dbSession = dbService.getDBSessionByKey(dbid);
		if (dbSession == null) {
			return false;
		}
		for (int rid : ridList) {
			for (IEntityDao dao : dbSession.getAllEntityDao()) {
				dao.createTable(dbSession.getParams(rid));
			}
		}
		return true;
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
}
