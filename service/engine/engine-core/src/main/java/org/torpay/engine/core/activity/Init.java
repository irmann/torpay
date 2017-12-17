package org.torpay.engine.core.activity;

import org.torpay.engine.util.ActivityNames;
import org.torpay.engine.workflow.Activity;
import org.torpay.engine.workflow.WorkflowContext;
import org.torpay.engine.workflow.WorkflowException;

public class Init extends BaseActivity implements Activity {

	public String getName() {
		return ActivityNames.INIT;
	}

	@Override
	protected void activityExecute(WorkflowContext context)
			throws WorkflowException {
	}

}
