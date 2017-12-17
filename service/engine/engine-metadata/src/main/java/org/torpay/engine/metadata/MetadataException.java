package org.torpay.engine.metadata;

import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCode;

public class MetadataException extends GlobalExcetion {

	public MetadataException(ErrorCode errorCode, String message,
			String activityName) {
		super(errorCode, message, activityName);

	}

	public MetadataException(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(errorCode, message, activityName, th);

	}

}
