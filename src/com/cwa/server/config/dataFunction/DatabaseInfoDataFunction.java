package com.cwa.server.config.dataFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.component.data.function.IDataFunction;
import com.cwa.data.config.IDatabaseInfoConfigDao;
import com.cwa.data.config.domain.DatabaseInfoConfig;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.service.constant.ServiceConstant;

public class DatabaseInfoDataFunction implements IDataFunction {
	private Map<Integer, DatabaseInfoConfig> entityMap = new HashMap<Integer, DatabaseInfoConfig>();

	// {组id:数据库id}按组自增
	private Map<Integer, Integer> dbIdMap = new HashMap<Integer, Integer>();

	private IConfigContext context;
	private IDatabaseInfoConfigDao dao;

	private int startDbId = 10000;

	private boolean isInited;

	public DatabaseInfoDataFunction(IConfigContext context) {
		this.context = context;
		dao = (IDatabaseInfoConfigDao) context.getDataFunctionManager().getDbSession().getEntityDao(DatabaseInfoConfig.class);
	}

	@Override
	public boolean isInited() {
		return isInited;
	}

	@Override
	public boolean initData(boolean newRegister) {
		if (isInited) {
			return false;
		}
		isInited = true;
		List<DatabaseInfoConfig> entitys = dao.selectAllEntity(createParams());
		for (DatabaseInfoConfig e : entitys) {
			setGroupDBId(e.gid, e.dbid);

			int key = getEKey(e.gid, e.dbid);
			entityMap.put(key, e);
		}
		return false;
	}

	public boolean increaseRegion(int gid, int dbid, List<Integer> ridList) {
		int key = getEKey(gid, dbid);
		DatabaseInfoConfig entity = entityMap.get(key);
		if (entity == null) {
			return false;
		}
		for (int rid : ridList) {
			synchronized (entity.getRegionMapList()) {
				if (entity.getRegionMapList().contains(rid)) {
					return false;
				}
				entity.getRegionMapList().add(rid);
			}
		}
		// 更新进数据库
		updateEntity(entity);
		return true;
	}

	public DatabaseInfoConfig createNewEntity(int gid, String ip, int port, String dbName, String user, String password, String mybatis,
			List<Integer> rids) {
		DatabaseInfoConfig entity = new DatabaseInfoConfig();
		entity.gid = gid;
		entity.dbid = createDbid(gid);
		entity.ip = ip;
		entity.port = port;
		entity.dbName = dbName;
		entity.user = user;
		entity.password = password;
		entity.mybatis = mybatis;
		entity.getRegionMapList().addAll(rids);
		insertEntity(entity);

		// 插入缓存
		int key = getEKey(entity.gid, entity.dbid);
		entityMap.put(key, entity);

		return entity;
	}

	private void setGroupDBId(int gid, int dbid) {
		Integer tdbId = dbIdMap.get(gid);
		if (tdbId == null || dbid > tdbId) {
			dbIdMap.put(gid, dbid);
		}
	}

	private int createDbid(int gid) {
		Integer tdbId = dbIdMap.get(gid);
		if (tdbId == null) {
			tdbId = startDbId;
		} else {
			tdbId += 1;
		}
		dbIdMap.put(gid, tdbId);
		return tdbId;
	}

	private int getEKey(int gid, int dbid) {
		return dbid * 1000 + gid;
	}

	public Collection<DatabaseInfoConfig> getDatabaseInfoConfigs() {
		return entityMap.values();
	}

	private void updateEntity(DatabaseInfoConfig entity) {
		dao.updateEntity(entity, context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid));
	}

	private void insertEntity(DatabaseInfoConfig entity) {
		dao.insertEntity(entity, context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid));
	}

	private Map<String, Object> createParams() {
		return context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid);
	}
}
