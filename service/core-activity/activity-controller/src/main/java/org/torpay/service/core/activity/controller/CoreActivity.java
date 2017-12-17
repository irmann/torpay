package org.torpay.service.core.activity.controller;

import org.torpay.common.request.ActionRequest;

public interface CoreActivity {

	public void execute(ActionRequest actionRequest) throws ActivityControllException;

	public String getName();

	public String getGroup();

	public String getFrontActivity();

	public String nextCoreActivity();

	public Boolean isMandatory();

	public void setActivityConditionHandler(
			ActivityConditionHandler activityConditionHandler);
	
	public void setActivityErrorHandler(
			ActivityErrorHandler activityConditionHandler);

	public ActivityErrorHandler  getActivityErrorHandler();
	
	public void checkPreCondition();

	public void preExecution();

	public void postExecution();

	public void setCurrentExecutionOrder(Integer order);

	public Integer getExpectedExecutionOrder();
	
	public Boolean isActive();

	public String getBlackList();

	public String getWhiteList();
	
	public Boolean isBlackList(ActionRequest actionRequest);

	public Boolean isWhiteList(ActionRequest actionRequest);

	public void setNextActivity(String nextActivity);

	public void setFrontActivity(String fronActivity);

	
}
