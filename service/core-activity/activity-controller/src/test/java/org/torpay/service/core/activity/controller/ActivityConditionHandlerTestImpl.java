package org.torpay.service.core.activity.controller;

import org.torpay.service.core.activity.controller.ActivityConditionHandler;

public class ActivityConditionHandlerTestImpl implements
		ActivityConditionHandler {

	public Boolean isExecutable(String activityName, String activityGroup) {
		return false;
	}

}
