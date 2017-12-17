package org.torpay.engine.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralErrorHandler implements ErrorHandler {
	private static final Logger LOG = LoggerFactory
			.getLogger(GeneralErrorHandler.class);

	public void handleError(WorkflowContext context, WorkflowException we) {
		String errorLog = we.getActivityName() + ":code<"
				+ we.getErrorCode().getCode() + ">,message<"
				+ we.getMessage() + ">";
		LOG.error(errorLog, we);
		context.appendActivityLog(errorLog);
		// TODO:log to a persist log repository like db
		context.getWorkflowResult().setWorkflowException(we);

	}

}
