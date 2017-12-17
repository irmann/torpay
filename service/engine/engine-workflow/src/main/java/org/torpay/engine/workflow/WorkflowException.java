package org.torpay.engine.workflow;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class WorkflowException extends GlobalExcetion {

	
	
	public WorkflowException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);
	}

}
