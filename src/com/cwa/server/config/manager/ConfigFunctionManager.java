package com.cwa.server.config.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import baseice.basedao.IEntity;

import com.cwa.component.data.IDBSession;
import com.cwa.component.data.function.IDataFunction;
import com.cwa.component.data.function.IDataFunctionManager;
import com.cwa.data.config.domain.DatabaseInfoConfig;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.server.config.dataFunction.DatabaseInfoDataFunction;
import com.cwa.service.constant.ServiceConstant;


public class ConfigFunctionManager implements IDataFunctionManager {
	// 数据session
	private IDBSession dbSession;
	// 数据功能map
	private Map<String, IDataFunction> dataFunctionMap = new HashMap<String, IDataFunction>();

	public void init(IConfigContext context) {
		// 获得公共区
		dbSession = context.getDbSession(ServiceConstant.General_Rid);

		dataFunctionMap.put(DatabaseInfoConfig.class.getSimpleName(), new DatabaseInfoDataFunction(context));
	}

	@Override
	public IDBSession getDbSession() {
		return dbSession;
	}

	@Override
	public IDataFunction getDataFunction(Class<? extends IEntity> cla) {
		return dataFunctionMap.get(cla.getSimpleName());
	}

	@Override
	public void initData() {
		for (Entry<String, IDataFunction> entry : dataFunctionMap.entrySet()) {
			entry.getValue().initData(false);
		}
	}

	@Override
	public void initData(Class<? extends IEntity> cla) {
		IDataFunction dataFunction = dataFunctionMap.get(cla.getSimpleName());
		if (dataFunction != null) {
			dataFunction.initData(false);
		}
	}

	@Override
	public void insertDataTimeout() {
	}

	@Override
	public void removeDataTimeout() {
	}

	@Override
	public void resetDataTimeout() {
	}
}
