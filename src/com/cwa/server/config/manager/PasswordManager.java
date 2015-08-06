package com.cwa.server.config.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwa.util.encryption.Md5Util;

/**
 * 密码管理
 * 
 * @author mausmars
 *
 */
public class PasswordManager {
	protected static final Logger logger = LoggerFactory.getLogger(PasswordManager.class);

	private String passwordFilePath;
	private String separator = "#";

	private String expectName = "admin";
	private String expectPasswd = "123456";

	private String secondName = "onlySee";
	private String secondPasswd = "onlySee";

	// 用户名，密码
	private ConcurrentHashMap<String, String> passwordMap = new ConcurrentHashMap<String, String>();

	public void init() throws IOException {
		loadPFile();
	}

	public boolean isPassword(String name, String password) {
		if (!passwordMap.containsKey(name)) {
			return false;
		}
		String p = passwordMap.get(name);
		String encodeP = Md5Util.md5Encode(password);
		return p.equals(encodeP);
	}

	public void modifyPassword(String name, String password) throws IOException {
		if (!passwordMap.containsKey(name)) {
			return;
		}
		String encodeP = Md5Util.md5Encode(password);
		passwordMap.put(name, encodeP);
		modifyPFile();
		if (logger.isInfoEnabled()) {
			logger.info("modifyPassword name=" + name + " encodeP=" + encodeP);
		}
	}

	private void loadPFile() throws IOException {
		File file = new File(passwordFilePath);
		if (!file.exists()) {
			String encodeP = Md5Util.md5Encode(expectPasswd);
			String encodePS = Md5Util.md5Encode(secondPasswd);
			passwordMap.put(expectName, encodeP);
			passwordMap.put(secondName, encodePS);
		} else {
			BufferedReader input = null;
			try {
				input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				for (String s = input.readLine(); s != null && !s.isEmpty();) {
					String[] ss = s.split(separator);
					passwordMap.put(ss[0], ss[1]);
					s = input.readLine();
				}
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}
	}

	private void modifyPFile() throws IOException {
		File file = new File(passwordFilePath);
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			for (Entry<String, String> entry : passwordMap.entrySet()) {
				output.write(entry.getKey() + separator + entry.getValue());
				output.newLine();
			}
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}

	public void setPasswordFilePath(String passwordFilePath) {
		this.passwordFilePath = passwordFilePath;
	}
}
