package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class ServiceExcetion extends GlobalExcetion {

	public ServiceExcetion(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

	public ServiceExcetion(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}
	
	public ServiceExcetion(ErrorCode errorCode, String message,
			String activityName, String[] errorMessageParameters, Throwable th) {
		super(errorCode, message,
				 activityName, errorMessageParameters,th);
	}

}
