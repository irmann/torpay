package org.torpay.service.core.activity.activities.generate;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityNames;

public class GenerateTransactionCode extends CoreActivityBase implements
		CoreActivity {
	static final Logger LOG = LoggerFactory
			.getLogger(GenerateTransactionCode.class);

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
		actionRequest.setTransactionCode(RandomStringUtils
				.randomAlphanumeric(10));
		LogUtil.logDebug(
				LOG,
				"generated transaction code "
						+ actionRequest.getTransactionCode(), actionRequest);
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
		return CoreActivityNames.GENERATE_TRANSACTION_CODE;
	}

	@Override
	public String getGroup() {
		return CoreActivityNames.GROUP_GENERATE;
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
