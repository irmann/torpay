package org.torpay.engine.workflow;

import java.util.HashMap;
import java.util.Map.Entry;

public class WorkflowContext {

	private WorkflowResult workflowResult = new WorkflowResult();
	private SeedData seedData;
	private StringBuffer activityLog = new StringBuffer();
	private HashMap<Object, Object> attribute = new HashMap<Object, Object>();
	private Activity currecntActivity;

	public boolean stopProcess() {
		return false;
	}

	public void setSeedData(SeedData seedData) {
		this.seedData = seedData;
		for (Entry entry : seedData.getData().entrySet())
			attribute.put(entry.getKey(), entry.getValue());
	}

	public WorkflowResult getWorkflowResult() {
		return workflowResult;
	}

	public StringBuffer getActivityLog() {
		return activityLog;
	}

	public void appendActivityLog(String log) {
		activityLog.append("<");
		activityLog.append(log);
		activityLog.append(">");
	}

	public Object getAttribute(String name) {
		return attribute.get(name);
	}

	public void setAttribute(String name, Object value) {
		this.attribute.put(name, value);
	}

	public Activity getCurrecntActivity() {
		return currecntActivity;
	}

	public void setCurrecntActivity(Activity currecntActivity) {
		this.currecntActivity = currecntActivity;
	}
}
