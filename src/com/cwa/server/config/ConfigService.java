package com.cwa.server.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import serverice.config.ServerInfo;
import baseice.data.config.DatabaseInfoGeneralConfig;

import com.cwa.component.data.IDBService;
import com.cwa.component.data.IDBSession;
import com.cwa.component.data.function.IDataFunctionManager;
import com.cwa.component.functionmanage.IFunctionService;
import com.cwa.data.config.domain.DatabaseInfoConfig;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.server.config.http.constant.UrlConstant;
import com.cwa.server.config.manager.ConfigFunctionManager;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.context.IGloabalContext;
import com.cwa.service.init.services.HttpService;

public class ConfigService implements IConfigService, IConfigContext {
	private DatabaseInfoConfig info = new DatabaseInfoConfig();

	private Map<Integer, AtomicInteger> idCreaterMap = new HashMap<Integer, AtomicInteger>();

	private IGloabalContext gloabalContext;
	private ConfigFunctionManager functionManager;

	@Override
	public void startup(IGloabalContext gloabalContext) throws Exception {
		this.gloabalContext = gloabalContext;

		HttpService httpService = (HttpService) gloabalContext.getCurrentService(ServiceConstant.HttpServerKey);
		UrlConstant urlConstant = new UrlConstant();
		urlConstant.init(gloabalContext.getOutsideNetIp(), httpService.getPort());

		functionManager = new ConfigFunctionManager();
		functionManager.init(this);
		functionManager.initData();
	}

	@Override
	public void shutdown() throws Exception {

	}

	@Override
	public IGloabalContext getGloabalContext() {
		return gloabalContext;
	}

	@Override
	public IDBSession getDbSession(int rid) {
		IDBService service = (IDBService) gloabalContext.getCurrentService(ServiceConstant.DatabaseKey);
		if (service == null) {
			return null;
		}
		return service.getDBSession(rid);
	}

	@Override
	public IFunctionService getFunctionService() {
		return gloabalContext.getCurrentFunctionService();
	}

	@Override
	public int getGid() {
		return gloabalContext.getGid();
	}

	@Override
	public IDataFunctionManager getDataFunctionManager() {
		return functionManager;
	}

	private int createId(int gid, int functionType) {
		// TODO 这里有问题要想其他解决办法
		int key = gid * 100 + functionType;

		AtomicInteger idCreater = null;
		synchronized (this) {
			idCreater = idCreaterMap.get(key);
			if (idCreater == null) {
				idCreater = new AtomicInteger(0);
				idCreaterMap.put(key, idCreater);
			}
		}
		return idCreater.incrementAndGet();
	}

	@Override
	public ServerInfo register(String ip, int gid, int functionType) {
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.address = ip;
		serverInfo.fid = createId(gid, functionType);
		serverInfo.version = gloabalContext.getStartTime();

		serverInfo.dbInfo = info;
		return serverInfo;
	}

	@Override
	public DatabaseInfoGeneralConfig getInfo() {
		return info;
	}

	public void setMybatis(String mybatis) {
		info.mybatis = mybatis;
	}

	public void setDbUser(String dbUser) {
		info.user = dbUser;
	}

	public void setDbPassword(String dbPassword) {
		info.password = dbPassword;
	}

	public void setDbName(String dbName) {
		info.dbName = dbName;
	}

	public void setDbIp(String dbIp) {
		info.ip = dbIp;
	}

	public void setDbPort(int dbPort) {
		info.port = dbPort;
	}

	public void setRegionMap(String regionMap) {
		info.regionMap = regionMap;
	}
}
