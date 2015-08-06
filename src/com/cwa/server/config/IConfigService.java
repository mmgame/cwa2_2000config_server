package com.cwa.server.config;

import com.cwa.service.IModuleServer;

import baseice.data.config.DatabaseInfoGeneralConfig;
import serverice.config.ServerInfo;

public interface IConfigService extends IModuleServer {

	// -----------------------------------
	ServerInfo register(String ip, int gid, int functionType);

	DatabaseInfoGeneralConfig getInfo();
}
