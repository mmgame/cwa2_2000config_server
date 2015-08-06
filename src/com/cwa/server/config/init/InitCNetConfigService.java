package com.cwa.server.config.init;

import serverice.config.ServerInfo;

import com.cwa.component.data.ISpreadEntity;
import com.cwa.server.config.IConfigService;
import com.cwa.service.constant.ServiceConstant;
import com.cwa.service.context.FilterContext;
import com.cwa.service.context.IGloabalContext;
import com.cwa.service.init.InitNetConfigService;
import com.cwa.util.NetUtil;

public class InitCNetConfigService extends InitNetConfigService {
	private IConfigService configService;
	
	@Override
	protected void initNetConfig2(FilterContext c) throws Exception {
		IGloabalContext gloabalContext = c.getGloabalContext();

		String ip = NetUtil.getInsideNetIp(gloabalContext.getMatchingNetwork());

		ServerInfo serverInfo = new ServerInfo();
		serverInfo.address = ip;
		serverInfo.fid = 1;
		serverInfo.dbInfo = configService.getInfo();
		((ISpreadEntity) serverInfo.dbInfo).obtainAfter();

		// 初始化网络配置
		c.putAttach(ServerInfo.class, serverInfo);

		createDBService(ServiceConstant.General_Gid, serverInfo, gloabalContext);
	}

	// -------------------------------
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}
