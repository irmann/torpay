package org.torpay.common.exception;

import org.torpay.common.util.ErrorCode;

public class GlobalExcetion extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected ErrorCode errorCode;
	private String activityName;
	private String[] messageParameters;

	public GlobalExcetion(ErrorCode errorCode, String message,
			String activityName) {
		super(message);
		this.errorCode = errorCode;
		this.activityName = activityName;
	}

	public GlobalExcetion(ErrorCode errorCode, String message,
			String activityName, Throwable th) {
		super(message, th);
		this.errorCode = errorCode;
		this.activityName = activityName;

	}

	public GlobalExcetion(ErrorCode errorCode, String message,
			String activityName, String[] messageParameters, Throwable th) {
		super(message, th);
		this.errorCode = errorCode;
		this.activityName = activityName;
		this.messageParameters = messageParameters;
	}

	public GlobalExcetion(ErrorCode errorCode, String message,
			String activityName, String[] messageParameters) {
		super(message);
		this.errorCode = errorCode;
		this.activityName = activityName;
		this.messageParameters = messageParameters;
	}

	public String getActivityName() {
		return activityName;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}

	public void setMessageParameters(String[] messageParameters) {
		this.messageParameters = messageParameters;
	}

}
