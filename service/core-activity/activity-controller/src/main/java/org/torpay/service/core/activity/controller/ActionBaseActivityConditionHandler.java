package org.torpay.service.core.activity.controller;

import org.torpay.common.request.ActionRequest;

public class ActionBaseActivityConditionHandler implements
		ActivityConditionHandler {
	public ActionRequest actionRequest;

	public ActionBaseActivityConditionHandler(ActionRequest actionRequest) {
		this.actionRequest = actionRequest;
	}

	public Boolean isExecutable(String activityName, String activityGroup) {
		
		return true;
	}

}
