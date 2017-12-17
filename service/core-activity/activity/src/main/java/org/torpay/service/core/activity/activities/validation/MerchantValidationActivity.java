package org.torpay.service.core.activity.activities.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityNames;


public class MerchantValidationActivity extends CoreActivityBase
		implements CoreActivity {

	static final Logger LOG = LoggerFactory
			.getLogger(MerchantValidationActivity.class);


	static {

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

	@Override
	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {

		LogUtil.logTrace(LOG, "start executing activity " + getName(), actionRequest);
		LogUtil.logDebug(LOG, "not implemented yet:" + getName(), actionRequest);
		//TODO implement me
		LogUtil.logTrace(LOG, "end executing activity " + getName(), actionRequest);
	}

	@Override
	public Boolean isMandatory() {
		return true;
	}

	@Override
	public Boolean isActive() {
		return true;
	}

	@Override
	public String getName() {
		return CoreActivityNames.MERCHANT_VALIDATION;
	}

	@Override
	public String getGroup() {
		return CoreActivityNames.GROUP_VALIDATION;
	}

	@Override
	protected ActivityConditionHandler getActivityConditionHandler() {
		return null;
	}

	@Override
	public String getBlackList() {
		return null;
	}

	@Override
	public String getWhiteList() {
		return null;
	}

}
