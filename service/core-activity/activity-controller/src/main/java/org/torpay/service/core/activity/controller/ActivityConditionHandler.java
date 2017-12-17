package org.torpay.service.core.activity.controller;

public interface ActivityConditionHandler {
	public Boolean isExecutable(String activityName, String activityGroup);
}
