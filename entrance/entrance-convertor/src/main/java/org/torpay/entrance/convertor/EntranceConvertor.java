package org.torpay.entrance.convertor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



//import org.springframework.test.context.ContextConfiguration;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.interfaces.ContentHandler;

//@ContextConfiguration(locations = { "classpath:entrance-core.xml" })
public interface EntranceConvertor {

	public ActionRequest httpToActionRequest(
			HttpServletRequest httpServletRequest, String serviceName,
			ContentHandler contentHandler,String content)
			throws EntranceException;

	public String actionResponseToHttp(ActionResponse actionResponse,
			HttpServletResponse httpServletResponse) throws EntranceException;

	public String excetionToHttp(GlobalExcetion serviceExcetion,
			HttpServletResponse httpServletResponse);
}
