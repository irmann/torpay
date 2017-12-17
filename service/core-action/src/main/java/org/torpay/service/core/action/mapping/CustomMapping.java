package org.torpay.service.core.action.mapping;

import org.torpay.common.exception.ActionException;
import org.torpay.common.request.ActionRequest;

public interface CustomMapping {

	public void convert(ActionRequest actionRequest) throws ActionException;
}
