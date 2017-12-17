package org.torpay.service.core.activity.activities.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityNames;
import org.torpay.service.proxy.ProxyPersistence;

public class StoreActionRequestActivity extends BaseActivityPersistence
		implements CoreActivity {
	static final Logger LOG = LoggerFactory
			.getLogger(StoreActionRequestActivity.class);

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
		LogUtil.logDebug(LOG, "action request before save" + actionRequest.toString(),
				actionRequest);
		Request request = BeanConvertor.convertToRequest(actionRequest);
		try {
			ProxyPersistence.getPersistenceCoreActivityService().saveRequest(request);
			actionRequest.setRequest_db_id(request.getId());
		} catch (PersistenceException pe) {
			throw new ActivityControllException(pe.getErrorCode(), pe.getMessage(),
					pe.getActivityName());
		}
		LogUtil.logDebug(LOG, "request is saved in db. " + request.toString(),
				actionRequest);
		LogUtil.logTrace(LOG, "end executing activity " + getName(), actionRequest);
	}

	@Override
	public Boolean isMandatory() {
		return true;
	}

	@Override
	public Boolean isActive() {
		// try {
		// return configurationService
		// .getBoolean(
		// ConfigurationKeys.SERVICE_CORE_ACTIVITY_PERSISTENCE_BASE_STORE_ACTION_REQUEST_ENABLE,
		// true);
		// } catch (ConfigurationException e) {
		// LOG.error("error during get configuration ", e);
		// }
		return true;
	}

	@Override
	public String getName() {
		return CoreActivityNames.STORE_ACTION_REQUEST;
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
