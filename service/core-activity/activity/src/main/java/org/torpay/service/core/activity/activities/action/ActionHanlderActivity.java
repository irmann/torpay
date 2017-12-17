package org.torpay.service.core.activity.activities.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.exception.ActionException;
import org.torpay.common.interfaces.ActionHandler;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.engine.metadata.MetadataException;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;
import org.torpay.service.core.activity.controller.CoreActivityNames;
import org.torpay.service.proxy.ProxyEngine;

public class ActionHanlderActivity extends CoreActivityBase implements
		CoreActivity {

	static final Logger LOG = LoggerFactory
			.getLogger(ActionHanlderActivity.class);

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
		String actionHandlerClass = lookupActionHandlerClass(
				actionRequest.getProvider(), actionRequest.getAction());
		LogUtil.logDebug(LOG, "the action hanlder class is found:"
				+ actionHandlerClass, actionRequest);
		ActionHandler actionHandler = createActionHandler(actionHandlerClass);
		try {
			actionHandler.execute(actionRequest);
		} catch (ActionException ae) {
			throw new ActivityControllException(ae.getErrorCode(),
					ae.getMessage(), ae.getActivityName());
		}
		LogUtil.logTrace(LOG, "end executing activity " + getName(),
				actionRequest);
	}

	private ActionHandler createActionHandler(String actionHandlerClass) {
		Class c = null;
		try {
			c = Class.forName(actionHandlerClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ActionHandler actionHandler = null;
		try {
			actionHandler = (ActionHandler) c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return actionHandler;
	}

	private String lookupActionHandlerClass(String provider, String action)
			throws ActivityControllException {
		if (provider == null)
			throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
					"provider is null", getName());
		if (action == null)
			throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
					"action is null", getName());
		try {
			String actionHanlderClass = ProxyEngine.getEngineMetaData()
					.extractActionHandlerClass(provider, action);
			if (actionHanlderClass == null)
				throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
						"actionHanlderClass is null", getName());
			return actionHanlderClass;
		} catch (MetadataException me) {
			throw new ActivityControllException(me.getErrorCode(),
					me.getMessage(), me.getActivityName());
		}
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
		return CoreActivityNames.ACTION_HANDLER;
	}

	@Override
	public String getGroup() {
		return CoreActivityNames.GROUP_CHECK;
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
