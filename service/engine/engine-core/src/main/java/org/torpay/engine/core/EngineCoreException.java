package org.torpay.engine.core;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class EngineCoreException extends GlobalExcetion {

	public EngineCoreException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

	public EngineCoreException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

}
