package org.torpay.common.util;

import org.apache.commons.io.FileUtils;

public class TorPayFileSystemUtil {

	public static String getTorPayUserHomeDirectory() {
		StringBuffer sb = new StringBuffer();
		sb.append(System.getProperty("user.home"));
		sb.append(System.getProperty("file.separator"));
		sb.append(Constants.TORPAY_USER_HOME_DIRECTORY);
		return sb.toString();
	}

	public static String getInstancePathFormTorPayUserHomeDirectory(
			String instance) {
		StringBuffer sb = new StringBuffer();
		sb.append(TorPayFileSystemUtil.getTorPayUserHomeDirectory());
		sb.append(System.getProperty("file.separator"));
		sb.append(instance);
		return sb.toString();
	}

	public static String getFielPathFormTorPayUserHomeDirectory(
			String instance, String fileName) {
		StringBuffer sb = new StringBuffer();
		sb.append(TorPayFileSystemUtil.getTorPayUserHomeDirectory());
		if (instance != null) {
			sb.append(System.getProperty("file.separator"));
			sb.append(instance);
		}
		sb.append(System.getProperty("file.separator"));
		sb.append(fileName);
		return sb.toString();
	}
}
