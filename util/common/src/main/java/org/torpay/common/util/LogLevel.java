package org.torpay.common.util;

public enum LogLevel {
	TRACE("TRACE"), DEBUG("DEBUG"), INFO("INFO"), WARN("WARN"), ERROR("ERROR"), FATAL(
			"FATAL");

	private String logLevel;

	private LogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

	public String getLogLevel() {
		return logLevel;
	}
}
