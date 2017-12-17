package org.torpay.service.core.activity.controller;

import org.torpay.common.request.ActionRequest;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityErrorHandler;

public class ActivityErrorHandlerDefaultImpl implements ActivityErrorHandler {
	public static Boolean VISITED = false;

	public void handelError(ActionRequest actionRequest, ActivityControllException se) {
		
	}

}
