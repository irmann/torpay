package org.torpay.service.core.activity.activities.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityConditionHandler;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityBase;

public class BaseActivityPersistence extends CoreActivityBase implements
		CoreActivity {
	static final Logger LOG = LoggerFactory
			.getLogger(BaseActivityPersistence.class);

	public void checkPreCondition() {
       throw new IllegalStateException("this method should be implemented by child class.");
	}

	public void preExecution() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	public void postExecution() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	public void setCurrentExecutionOrder(Integer order) {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	public Integer getExpectedExecutionOrder() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public void executeChild(ActionRequest actionRequest)
			throws ActivityControllException {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public Boolean isMandatory() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public Boolean isActive() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getGroup() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	protected ActivityConditionHandler getActivityConditionHandler() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public String getBlackList() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

	@Override
	public String getWhiteList() {
		throw new IllegalStateException("this method should be implemented by child class.");
	}

}
