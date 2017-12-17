package org.torpay.engine.workflow;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component("workflowProcessor")
public class WorkflowProcessor {
	private List<Activity> activities;
	private ErrorHandler defaultErrorHandler;

	private WorkflowContext processContextClass;

	public boolean supports(Activity activity) {
		return false;
	}

	public void doActivities() {

	}

	public WorkflowResult doActivities(SeedData seedData) {

		// Retrieve injected by Spring
		List activities = getActivities();

		// Retrieve a new instance of the Workflow ProcessContext
		WorkflowContext context = createContext();

		if (seedData != null)
			context.setSeedData(seedData);

		// Execute each activity in sequential order
		for (Iterator<Activity> it = activities.iterator(); it.hasNext();) {

			Activity activity = (Activity) it.next();

			try {
				activity.execute(context);

			} catch (WorkflowException wex) {
				// Determine if an error handler is available at the activity
				// level
				ErrorHandler errorHandler = activity.getErrorHandler();
				if (errorHandler == null) {
					getDefaultErrorHandler().handleError(context, wex);
					break;
				} else {
					// Handle error using default handler
					errorHandler.handleError(context, wex);
				}
				break;
			}

			// Ensure it's ok to continue the process
			if (processShouldStop(context, activity))
				break;
		}
		return context.getWorkflowResult();

	}

	private boolean processShouldStop(WorkflowContext context, Activity activity) {
		// TODO Auto-generated method stub
		return false;
	}

	private ErrorHandler getDefaultErrorHandler() {
		// TODO Auto-generated method stub
		return defaultErrorHandler;
	}

	private WorkflowContext createContext() {
		return getProcessContextClass();
	}

	private List getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
		this.defaultErrorHandler = defaultErrorHandler;
	}

	public WorkflowContext getProcessContextClass() {
		return processContextClass;
	}

	public void setProcessContextClass(WorkflowContext processContextClass) {
		this.processContextClass = processContextClass;
	}
}

// BaseProcessor processor =
// (BaseProcessor)context.getBean("rateDropProcessor");
// processor.doActivities(createSeedData());