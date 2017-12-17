package org.torpay.service.core.activity.controller;


import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;

public interface ActivityController {

	public void process(ActionRequest actionRequest,
			ActionResponse actionResponse) throws ActivityControllException;
}
