package org.torpay.service.core.activity.activities;

import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityErrorHandler;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityNames;

public class InitCoreActivity extends CoreActivityBase implements CoreActivity {
	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {

	}

	public String getName() {
		return CoreActivityNames.INIT;
	}

	public String getGroup() {
		return null;
	}

	public String getBlackList() {
		return null;
	}

	public String getWhiteList() {
		return null;
	}

	public Boolean isActive() {
		return null;
	}

	public Boolean isMandatory() {
		return true;
	}

	public void checkPreCondition() {

	}

	public void preExecution() {

	}

	public void postExecution() {

	}

	public void setCurrentExecutionOrder(Integer order) {

	}

	public Integer getExpectedExecutionOrder() {
		return null;
	}

	public Boolean isBlackList(String activityName, String activityGroup) {
		return null;
	}

	public Boolean isWhiteList(String activityName, String activityGroup) {
		return null;
	}

	public int compare(CoreActivity coreActivity) {
		return 0;
	}

	protected ActivityConditionHandler getActivityConditionHandler() {
		return null;
	}

	@Override
	public ActivityErrorHandler getActivityErrorHandler() {
		return null;
	}
}
