package org.torpay.common.request;

public class ActionResponse {

	private String merchant;

	public class Detail {
		private String technicalErrorMessage;
		private String stackTrace;
		private String fullStackTraceMessages;
		private String rootCauseStackTrace;

		public String getTechnicalErrorMessage() {
			return technicalErrorMessage;
		}

		public void setTechnicalErrorMessage(String technicalErrorMessage) {
			this.technicalErrorMessage = technicalErrorMessage;
		}

		public String getStackTrace() {
			return stackTrace;
		}

		public void setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
		}

		public String getRootCauseStackTrace() {
			return rootCauseStackTrace;
		}

		public void setRootCauseStackTrace(String rootCauseStackTrace) {
			this.rootCauseStackTrace = rootCauseStackTrace;
		}

		public String getFullStackTraceMessages() {
			return fullStackTraceMessages;
		}

		public void setFullStackTraceMessages(String fullStackTraceMessages) {
			this.fullStackTraceMessages = fullStackTraceMessages;
		}

	};

	private Integer errorCode;
	private String status;
	private String message;
	private String message2;
	private String messageStatus;
	private String[] messageParameters;
	private Detail detail = new Detail();
	private Long entranceReference;

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errrorCode) {
		this.errorCode = errrorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

	public Long getEntranceReference() {
		return entranceReference;
	}

	public void setEntranceReference(Long entranceReference) {
		this.entranceReference = entranceReference;
	}

	public String[] getMessageParameters() {
		return messageParameters;
	}

	public void setMessageParameters(String[] messageParameters) {
		this.messageParameters = messageParameters;
	}

	public String getFilledMessage() {
		if (messageParameters != null && message != null) {
			for (int i = 0; i < messageParameters.length; i++)
				message = message.replace("$" + (i + 1), messageParameters[i]);
		}
		return message;
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

}
