package org.torpay.service.core.activity.activities.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.activities.persistence.StoreActionRequestActivity;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityErrorHandler;

public class ActivityErrorHandlerImpl implements ActivityErrorHandler {
	static final Logger LOG = LoggerFactory
			.getLogger(ActivityErrorHandlerImpl.class);

	public void handelError(ActionRequest actionRequest,
			ActivityControllException se) {
		try {
			new StoreActionRequestActivity().execute(actionRequest);
		} catch (ActivityControllException e) {
			LOG.error("error during Activity Error Handling", e);
			actionRequest.appendLog("error during Activity Error Handling "
					+ e.getMessage());
		}
	}

}
