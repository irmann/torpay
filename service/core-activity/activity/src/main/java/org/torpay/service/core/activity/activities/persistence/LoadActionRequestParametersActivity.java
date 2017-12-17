package org.torpay.service.core.activity.activities.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.activities.persistence.function.LoadParameters;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityNames;
import org.torpay.service.proxy.ProxyPersistence;

public class LoadActionRequestParametersActivity extends
		BaseActivityPersistence implements CoreActivity {
	static final Logger LOG = LoggerFactory
			.getLogger(LoadActionRequestParametersActivity.class);

	public void checkPreCondition() {

	}

	public void preExecution() {

	}

	public void postExecution() {

	}

	public void setCurrentExecutionOrder(Integer order) {

	}

	public Integer getExpectedExecutionOrder() {
		return 1;
	}

	@Override
	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {
		LogUtil.logTrace(LOG, "start executing activity " + getName(), actionRequest);
		LogUtil.logDebug(LOG, "loading request parameters from db for request_id"
				+ actionRequest.getRequest_db_id(), actionRequest);
		try {
			actionRequest.setValues(LoadParameters.load(
					actionRequest.getRequest_db_id(),
					ProxyPersistence.getPersistenceCoreActivityService()));
		} catch (PersistenceException pe) {
			throw new ActivityControllException(pe.getErrorCode(), pe.getMessage(),
					pe.getActivityName());
		}
		LogUtil.logDebug(
				LOG,
				"request parameters is loaded from db:"
						+ actionRequest.toStringValues(), actionRequest);
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
		return CoreActivityNames.LOAD_ACTION_REQUEST_PARAMETER;
	}

	@Override
	public String getGroup() {
		return CoreActivityNames.GROUP_PERSISTENCE;
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
