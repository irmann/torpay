package org.torpay.persistence.service;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class PersistenceException extends GlobalExcetion {

	public PersistenceException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

}
