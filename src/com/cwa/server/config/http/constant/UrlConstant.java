package com.cwa.server.config.http.constant;

import java.util.Hashtable;
import java.util.Map;

public class UrlConstant {
	private static String url;
	// ----------------------------------------------------------------------------
	private final static Map<String, String> urlMap = new Hashtable<String, String>();
	// servlet路径-------------------------------------------------
	public final static String context_login = "/login";

	public final static String context_increaseRegionAndDBinfo = "/increaseRegionAndDBinfo";
	public final static String context_increaseRegion = "/increaseRegion";
	public final static String context_increaseRegionInfo = "/increaseRegionInfo";

	public final static String context_showDBConfig = "/showDBConfig";
	public final static String context_showRegionInfo = "/showRegionInfo";
	// jsp路径-------------------------------------------------
	public final static String jsp_login = "/login.jsp";
	public final static String jsp_main = "/main.jsp";
	public final static String jsp_error = "/error.jsp";

	public final static String jsp_increaseRegion = "/increaseRegion.jsp";
	public final static String jsp_increaseRegionAndDBinfo = "/increaseRegionAndDBinfo.jsp";
	public final static String jsp_increaseRegionInfo = "/increaseRegionInfo.jsp";

	public final static String jsp_showDBConfig = "/showDBConfig.jsp";
	public final static String jsp_showRegionInfo = "/showRegionInfo.jsp";
	// -------------------------------------------------
	public final static String webApp = "webapp";
	// -------------------------------------------------
	private static String u;

	public final static long S_OUTTIME = 60 * 1000;

	public void init(String ip, int port) {
		url = ip + ":" + port;
		u = "http://" + url;

		urlMap.put(jsp_login, u + jsp_login);
		urlMap.put(jsp_main, u + jsp_main);
		urlMap.put(jsp_error, u + jsp_error);
		// ------------------------------
		urlMap.put(jsp_increaseRegion, u + jsp_increaseRegion);
		urlMap.put(jsp_increaseRegionAndDBinfo, u + jsp_increaseRegionAndDBinfo);
		urlMap.put(jsp_increaseRegionInfo, u + jsp_increaseRegionInfo);

		urlMap.put(jsp_showDBConfig, u + jsp_showDBConfig);
		urlMap.put(jsp_showRegionInfo, u + jsp_showRegionInfo);
		// ------------------------------
		urlMap.put(context_login, u + context_login);

		urlMap.put(context_increaseRegionAndDBinfo, u + context_increaseRegionAndDBinfo);
		urlMap.put(context_increaseRegion, u + context_increaseRegion);
		urlMap.put(context_increaseRegionInfo, u + context_increaseRegionInfo);

		urlMap.put(context_showDBConfig, u + context_showDBConfig);
		urlMap.put(context_showRegionInfo, u + context_showRegionInfo);
	}

	public static String getUrl(String key) {
		return urlMap.get(key);
	}
}
