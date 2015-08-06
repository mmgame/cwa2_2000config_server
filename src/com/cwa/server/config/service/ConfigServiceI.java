package com.cwa.server.config.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.server.config.IConfigService;

import serverice.config.ServerInfo;
import serverice.config._IConfigServiceDisp;
import Ice.Current;

public class ConfigServiceI extends _IConfigServiceDisp {
	protected static final Logger logger = LoggerFactory.getLogger(ConfigServiceI.class);

	private static final long serialVersionUID = 1L;

	private IConfigService configService;

	@Override
	public ServerInfo register(int gid, int functionType, Current __current) {
		String remoteIp = null;
		try {
			remoteIp = getRemoteIp(__current);
		} catch (IOException e) {
			logger.error("", e);
		}
		if (remoteIp == null) {
			return null;
		}
		return configService.register(remoteIp, gid, functionType);
	}

	private String getRemoteIp(Current current) throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader(current.con._toString()));
		for (;;) {
			String str = reader.readLine();
			if (str == null) {
				break;
			}
			if (str.indexOf("remote") != -1) {
				String ip = str.split("=")[1].split(":")[0].trim();
				return ip;
			}
		}
		return null;
	}

	// --------------------------------------
	public void setConfigService(IConfigService configService) {
		this.configService = configService;
	}
}
