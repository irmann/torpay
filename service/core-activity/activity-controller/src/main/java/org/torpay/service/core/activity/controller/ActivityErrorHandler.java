package org.torpay.service.core.activity.controller;

import org.torpay.common.request.ActionRequest;

public interface ActivityErrorHandler {
	public void handelError(ActionRequest actionRequest, ActivityControllException se);
}
