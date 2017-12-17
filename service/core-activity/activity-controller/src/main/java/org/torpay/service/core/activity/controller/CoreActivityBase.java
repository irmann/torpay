package org.torpay.service.core.activity.controller;

import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.util.ErrorCodes;

abstract public class CoreActivityBase implements Comparable<CoreActivityBase> {
	private Integer order;
	private String nextActivity;
	private String frontActivity;

	static final Logger LOG = LoggerFactory.getLogger(CoreActivityBase.class);
	private ActivityConditionHandler activityConditionHandler;
	private ActivityErrorHandler activityErrorHandler;

	abstract public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException;

	abstract public Boolean isMandatory();

	abstract public Boolean isActive();

	abstract public String getName();

	abstract public String getGroup();

	abstract protected ActivityConditionHandler getActivityConditionHandler();

	abstract public String getBlackList();

	abstract public String getWhiteList();

	public void execute(ActionRequest actionRequest) throws ActivityControllException {
		logDebug("executing " + getName());
		if (actionRequest == null) {
			throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
					"actionRequest is null", getName());
		}
		if (getActivityConditionHandler() != null)
			setActivityConditionHandler(getActivityConditionHandler());
		else
			setActivityConditionHandler(new ActivityConditionHandlerDefautlImpl());

		if (getActivityErrorHandler() == null)
			setActivityErrorHandler(new ActivityErrorHandlerDefaultImpl());

		if (!checkPermission(actionRequest))
			return;
		executeChild(actionRequest);

	}

	private boolean checkPermission(ActionRequest actionRequest)
			throws ActivityControllException {
		if (isMandatory() != null && isMandatory()) {
			logDebug("activity is mandatory");
			return true;
		}
		Boolean executable = activityConditionHandler.isExecutable(getName(),
				getGroup());
		if (executable != null && !executable) {
			logDebug("activity is not Executable");
			return false;
		}
		if (isActive() != null && !isActive()) {
			logDebug("activity is not Active");
			return false;
		}

		if (getBlackList() != null && getWhiteList() != null)
			throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR, "activity "
					+ getName()
					+ " has blacklist and whitelist. only one is permitted",
					getName());
		if (isBlackList(actionRequest) != null && isBlackList(actionRequest)
				&& getBlackList() != null) {
			logDebug("activity is blacklisted");
			return false;
		}

		if (isWhiteList(actionRequest) != null && !isWhiteList(actionRequest)
				&& getWhiteList() != null) {
			logDebug("activity is not in whiltelist");
			return false;
		}

		return true;
	}

	private void logDebug(String msg) {
		LOG.debug(getName() + ":" + msg);
	}

	public Boolean isBlackList(ActionRequest actionRequest) {
		String blacklist = getBlackList();
		if (blacklist != null) {
			StringTokenizer st = new StringTokenizer(blacklist, ",");
			while (st.hasMoreElements()) {
				String item = (String) st.nextElement();
				if (actionRequest.getAction() != null
						&& actionRequest.getProvider() != null
						&& item.equals(actionRequest.getProvider() + "."
								+ actionRequest.getAction()))
					return true;
				else if (actionRequest.getProvider() != null
						&& item.equals(actionRequest.getProvider() + ".*"))
					return true;
			}
		}
		return false;
	}

	public Boolean isWhiteList(ActionRequest actionRequest) {
		String whitelist = getWhiteList();
		if (whitelist != null) {
			StringTokenizer st = new StringTokenizer(whitelist, ",");
			while (st.hasMoreElements()) {
				String item = (String) st.nextElement();
				if (actionRequest.getAction() != null
						&& actionRequest.getProvider() != null
						&& item.equals(actionRequest.getProvider() + "."
								+ actionRequest.getAction()))
					return true;
				else if (actionRequest.getProvider() != null
						&& item.equals(actionRequest.getProvider() + ".*"))
					return true;
			}
		}

		return false;
	}

	public void setActivityErrorHandler(
			ActivityErrorHandler activityErrorHandler) {
		this.activityErrorHandler = activityErrorHandler;
	}

	public ActivityErrorHandler getActivityErrorHandler() {
		return activityErrorHandler;
	}

	public int compareTo(CoreActivityBase coreActivityBase) {
		if (this.order != null && coreActivityBase.order != null)
			return this.order.intValue() - coreActivityBase.order.intValue();
		return 0;
	}

	public void setActivityConditionHandler(
			ActivityConditionHandler activityConditionHandler) {
		this.activityConditionHandler = activityConditionHandler;
	}

	public void setNextActivity(String nextActivity) {
		this.nextActivity = nextActivity;
	}

	public void setFrontActivity(String frontActivity) {
		this.frontActivity = frontActivity;

	}

	public String nextCoreActivity() {
		return nextActivity;
	}

	public String getFrontActivity() {
		return this.frontActivity;
	}
}
