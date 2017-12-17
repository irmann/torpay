package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class ActionHanlderException extends GlobalExcetion {
	public ActionHanlderException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

	public ActionHanlderException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);

	}
}
