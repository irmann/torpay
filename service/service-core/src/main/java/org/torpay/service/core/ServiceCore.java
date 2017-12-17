package org.torpay.service.core;

import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.exception.ServiceException;
import org.torpay.common.interfaces.ContentHandler;

public interface ServiceCore {

	public ActionResponse process(ActionRequest actionRequest);

	public ContentHandler getContentHandler() throws ServiceException;
	
	public void storeRequestLog(String request,String response,Long reference)throws ServiceException;

}
