package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class ServiceException extends GlobalExcetion {

	private static final long serialVersionUID = 3189507337096021984L;

	public ServiceException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

	public ServiceException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);

	}
	
}
