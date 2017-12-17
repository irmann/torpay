package org.torpay.engine.validation;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class ValidationException extends GlobalExcetion {

	public ValidationException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

	public ValidationException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

	public ValidationException(ErrorCode errorCode, String message,
			String activityName, String[] errorMessageParameters,Throwable th) {
		super(errorCode, message,
				 activityName, errorMessageParameters,th);
	}
	
	public ValidationException(ErrorCode errorCode, String message,
			String activityName, String[] errorMessageParameters) {
		super(errorCode, message,
				 activityName, errorMessageParameters);
	}
}
