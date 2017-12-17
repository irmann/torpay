package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class ActionException extends ServiceException {

	public ActionException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

	public ActionException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

}
