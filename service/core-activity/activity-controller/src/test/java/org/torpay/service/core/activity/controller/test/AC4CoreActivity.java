package org.torpay.service.core.activity.controller.test;

import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityErrorHandler;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityConstantTest;
import org.torpay.service.core.activity.controller.CoreActivityNames;

public class AC4CoreActivity extends CoreActivityBase implements CoreActivity {
	public static Boolean VISITED = false;

	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {
		VISITED = true;

	}

	public String getName() {
		return CoreActivityConstantTest.AC_4;
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
