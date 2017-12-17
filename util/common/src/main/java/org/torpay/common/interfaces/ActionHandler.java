package org.torpay.common.interfaces;

import org.torpay.common.exception.ActionException;
import org.torpay.common.request.ActionRequest;

public interface ActionHandler {
	public void execute(ActionRequest actionRequest) throws ActionException;
}
