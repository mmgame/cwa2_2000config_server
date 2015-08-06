package com.cwa.server.config.dataFunction;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwa.component.data.function.IDataFunction;
import com.cwa.data.entity.IRegionEntityDao;
import com.cwa.data.entity.domain.RegionEntity;
import com.cwa.server.config.context.IConfigContext;
import com.cwa.service.constant.ServiceConstant;

public class RegionDataFunction implements IDataFunction {
	private IConfigContext context;

	private IRegionEntityDao dao;

	// {区id:RegionEntity}
	private Map<Integer, RegionEntity> entityMap = new HashMap<Integer, RegionEntity>();

	private boolean isInited;

	public RegionDataFunction(IConfigContext context) {
		this.context = context;
		dao = (IRegionEntityDao) context.getDataFunctionManager().getDbSession().getEntityDao(RegionEntity.class);
	}

	@Override
	public boolean initData(boolean newRegister) {
		if (isInited) {
			return false;
		}
		isInited = true;

		List<RegionEntity> entitys = dao.selectAllEntity(createParams());
		for (RegionEntity e : entitys) {
			entityMap.put(e.rid, e);
		}
		return false;
	}

	@Override
	public boolean isInited() {
		return isInited;
	}

	public RegionEntity createNewEntity(int rid, String name, int state, int userState) {
		RegionEntity entity = new RegionEntity();
		entity.rid = rid;
		entity.name = name;
		entity.state = state;
		entity.useState = userState;

		insertEntity(entity);
		entityMap.put(entity.rid, entity);
		return entity;
	}

	public Collection<RegionEntity> getEntitys() {
		return entityMap.values();
	}

	private void updateEntity(RegionEntity entity) {
		dao.updateEntity(entity, context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid));
	}

	private void insertEntity(RegionEntity entity) {
		dao.insertEntity(entity, context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid));
	}

	private Map<String, Object> createParams() {
		return context.getDataFunctionManager().getDbSession().getParams(ServiceConstant.General_Rid);
	}
}
