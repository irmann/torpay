package org.torpay.engine.workflow;


import org.torpay.engine.workflow.ErrorHandler;
import org.torpay.engine.workflow.WorkflowContext;
import org.torpay.engine.workflow.WorkflowException;

public interface Activity {

	public void execute(WorkflowContext context) throws WorkflowException;

	public ErrorHandler getErrorHandler();

	public String getName();
	
	

}
