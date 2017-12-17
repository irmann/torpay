package org.torpay.configuration;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class ConfigurationException extends GlobalExcetion{

	public ConfigurationException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

}
