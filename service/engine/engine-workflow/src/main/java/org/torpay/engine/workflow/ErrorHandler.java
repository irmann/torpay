package org.torpay.engine.workflow;


public interface ErrorHandler {

	void handleError(WorkflowContext context, WorkflowException we);

}
