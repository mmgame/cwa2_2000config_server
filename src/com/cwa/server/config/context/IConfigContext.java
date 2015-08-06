package com.cwa.server.config.context;

import com.cwa.component.data.IDBSession;
import com.cwa.component.data.function.IDataFunctionManager;
import com.cwa.component.functionmanage.IFunctionService;
import com.cwa.service.context.IGloabalContext;

/**
 * 逻辑服务上下文
 * 
 * @author tzy
 * 
 */
public interface IConfigContext {
	/**
	 * 
	 * @return
	 */
	IGloabalContext getGloabalContext();

	/**
	 * 
	 * @param rid
	 * @return
	 */
	IDBSession getDbSession(int rid);

	/**
	 * 
	 * @return
	 */
	IFunctionService getFunctionService();

	/**
	 * 
	 * @return
	 */
	int getGid();

	IDataFunctionManager getDataFunctionManager();
}
