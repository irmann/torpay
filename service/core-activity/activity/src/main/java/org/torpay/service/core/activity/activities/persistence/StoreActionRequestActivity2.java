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

public class StoreActionRequestActivity2 extends StoreActionRequestActivity
		implements CoreActivity {
	static final Logger LOG = LoggerFactory
			.getLogger(StoreActionRequestActivity2.class);
	@Override
	public String getName() {
		return CoreActivityNames.STORE_ACTION_REQUEST_2;
	}
	
}
