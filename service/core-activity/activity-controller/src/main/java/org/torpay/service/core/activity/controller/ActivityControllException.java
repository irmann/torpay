package org.torpay.service.core.activity.controller;

import org.torpay.common.exception.ServiceExcetion;
import org.torpay.common.util.ErrorCode;

public class ActivityControllException extends ServiceExcetion {

	private static final long serialVersionUID = 3189507337096021984L;

	public ActivityControllException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

	public ActivityControllException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);

	}
	
	public ActivityControllException(ErrorCode errorCode, String message,
			String activityName, String[] errorMessageParameters, Throwable th) {
		super(errorCode, message,
				 activityName, errorMessageParameters,th);
	}
}
