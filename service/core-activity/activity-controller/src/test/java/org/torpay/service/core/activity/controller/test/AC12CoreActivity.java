package org.torpay.service.core.activity.controller.test;

import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityConditionHandlerTestImpl;
import org.torpay.service.core.activity.controller.ActivityErrorHandler;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityConstantTest;

public class AC12CoreActivity extends CoreActivityBase implements CoreActivity {
	public static Boolean VISITED = false;
	public static String NEXT = CoreActivityConstantTest.AC_13;

	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {
		VISITED = true;

	}

	public String getName() {
		return CoreActivityConstantTest.AC_12;
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
		return false;
	}

	public Boolean isMandatory() {
		return null;
	}

	public void setActivityConditionHandler(
			ActivityConditionHandler activityConditionHandler) {
		super.setActivityConditionHandler(new ActivityConditionHandlerTestImpl());
	}

	protected ActivityConditionHandler getActivityConditionHandler() {
		return new ActivityConditionHandlerTestImpl();
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

	@Override
	public ActivityErrorHandler getActivityErrorHandler() {
		return null;
	}
}
