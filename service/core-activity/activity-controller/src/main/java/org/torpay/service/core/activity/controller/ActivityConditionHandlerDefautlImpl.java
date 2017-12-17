package org.torpay.service.core.activity.controller;

public class ActivityConditionHandlerDefautlImpl implements
		ActivityConditionHandler {

	public Boolean isExecutable(String activityName, String activityGroup) {
		return true;
	}

}
