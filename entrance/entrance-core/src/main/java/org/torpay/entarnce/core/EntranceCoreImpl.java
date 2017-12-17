package org.torpay.entarnce.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.configuration.ConfigurationException;
import org.torpay.configuration.ConfigurationKeys;
import org.torpay.configuration.ConfigurationService;
import org.torpay.entrance.convertor.EntranceConvertor;
import org.torpay.service.core.ServiceCore;
import org.torpay.util.parent.BaseTorPayBean;

@Component("entranceCore")
public class EntranceCoreImpl extends BaseTorPayBean implements EntranceCore {
	static final Logger LOG = LoggerFactory.getLogger(EntranceCoreImpl.class);

	@Autowired
	private EntranceConvertor entranceConvertor;

	@Autowired
	private ConfigurationService configurationService;

	@Autowired
	private ServiceCore serviceCore;

	public String processWebRequest(HttpServletRequest request,
			HttpServletResponse response, String content)
			throws EntranceException {
		String responseLog = null;
		Long reference = null;
		ActionRequest actionRequest = null;
		try {
			// TODO:add test unit for this function
			String serviceName = configurationService.getValue(
					ConfigurationKeys.SERVICE_NAME, null);

			actionRequest = entranceConvertor.httpToActionRequest(request,
					serviceName, serviceCore.getContentHandler(),content);
			if (LOG.isDebugEnabled())
				LOG.debug(actionRequest.toString());
			ActionResponse actionResponse = serviceCore.process(actionRequest);
			reference = actionResponse.getEntranceReference();
			reviseResponseMessage(actionResponse);
			actionResponse.setMessage(actionRequest.getMerchant());
			responseLog = entranceConvertor.actionResponseToHttp(
					actionResponse, response);
		} catch (GlobalExcetion ge) {
			// TODO:add test unit for this catch
			LOG.error(
					"error in EntranceCore" 
							+ EntranceCoreUtil
									.requestToString(request, content), ge);
			if (actionRequest != null)
				LOG.error(actionRequest.toString());
			responseLog = entranceConvertor.excetionToHttp(ge, response);
		} finally {
			storeRequestLog(EntranceCoreUtil.requestToString(request, content),
					responseLog, reference);
		}
		return responseLog;
	}

	private void reviseResponseMessage(ActionResponse actionResponse) {
		String message = configurationService.getErrorCodeMessage(
				actionResponse.getStatus(), "en");
		if (message != null) {
			actionResponse.setMessage2(actionResponse.getMessage());
			actionResponse.setMessage(message);
		}

	}

	private void storeRequestLog(String request, String response, Long reference) {
		try {
			
			// TODO:add test unit for this function
			if (isStoreRawRequestActive()) {
				serviceCore.storeRequestLog(request,response,reference);
			}
		} catch (Throwable t) {
			LOG.error("can not store request log", t);
		}

	}

	private Boolean isStoreRawRequestActive() throws ConfigurationException {
		// TODO:add test unit for this function
		return configurationService.getBoolean(
				ConfigurationKeys.STORE_REQUEST_LOG, false);

	}
}
