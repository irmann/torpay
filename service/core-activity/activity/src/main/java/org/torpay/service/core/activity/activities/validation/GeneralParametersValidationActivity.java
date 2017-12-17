package org.torpay.service.core.activity.activities.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.engine.validation.ValidationException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityNames;
import org.torpay.service.proxy.ProxyEngine;

public class GeneralParametersValidationActivity extends CoreActivityBase
		implements CoreActivity {

	static final Logger LOG = LoggerFactory
			.getLogger(GeneralParametersValidationActivity.class);

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

		LogUtil.logTrace(LOG, "start executing activity " + getName(),
				actionRequest);
		try {
			ProxyEngine.getEngineValidation().validate(
					actionRequest.getProvider(), actionRequest.getAction(),
					actionRequest.getValues());
		} catch (ValidationException ve) {
			throw new ActivityControllException(ve.getErrorCode(),
					ve.getMessage(), ve.getActivityName(),
					ve.getMessageParameters(), ve);

		} catch (Exception e) {
			new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
					"error during validating request parameters",
					"validationActivity", e);
		}
		LogUtil.logTrace(LOG, "end executing activity " + getName(),
				actionRequest);
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
		return CoreActivityNames.GENERAL_PARAMETERS_VALIDATION;
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
