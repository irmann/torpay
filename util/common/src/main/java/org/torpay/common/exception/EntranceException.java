package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class EntranceException extends GlobalExcetion {

	private static final long serialVersionUID = 6823553478893559964L;

	public EntranceException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);
	}

	public EntranceException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}
}
