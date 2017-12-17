package org.torpay.service.core.activity.activities.persistence;

import java.util.Date;

import org.joda.time.DateTime;
import org.torpay.common.request.ActionRequest;
import org.torpay.persistence.model.Request;

public class BeanConvertor {

	public static Request convertToRequest(ActionRequest actionRequest) {
		Request request = new Request();
		request.setAction(actionRequest.getAction());
		request.setProvider(actionRequest.getProvider());
		request.setMerchant(actionRequest.getMerchant());
		request.setParameters(actionRequest.toStringParameters());
		request.setRequestContent(actionRequest.getRequest_content());
		request.setRequestString(actionRequest.getRequest_string());
		request.setRequestParameters(actionRequest.getRequest_parameters());
		request.setPassword(actionRequest.getPassword());
		request.setToken(actionRequest.getToken());
		request.setSecurityCode(actionRequest.getSecurity_code());
		request.setPaymentNumber(actionRequest.getPayment_number());
		request.setClientRequestId(actionRequest.getRequest_id());
		request.setExternalReference1(actionRequest.getExternalReference1());
		request.setExternalReference2(actionRequest.getExternalReference2());
		request.setExternalReference3(actionRequest.getExternalReference3());
		request.setLog(actionRequest.getLog().toString());
		request.setCreator(null);
		request.setCreationDate(new DateTime(new Date()));
		request.setId(actionRequest.getRequest_db_id());
		request.setTransactionCode(actionRequest.getTransactionCode());
		request.setTraceNumber(actionRequest.getTraceNumber());
		request.setType(actionRequest.getRequest_type());
		if (actionRequest.getActionResponse() != null) {
			if (actionRequest.getActionResponse().getErrorCode() != null)
				request.setResponseErrorCode(actionRequest.getActionResponse()
						.getErrorCode().toString());
			request.setResponseStatus(actionRequest.getActionResponse()
					.getStatus());
			request.setResponseMessage(actionRequest.getActionResponse()
					.getMessage());
			if (actionRequest.getActionResponse().getEntranceReference() != null)
				request.setResponseEntranceReference(actionRequest
						.getActionResponse().getEntranceReference().toString());
			request.setResponseTechnicalErrorMessage(actionRequest
					.getActionResponse().getDetail().getTechnicalErrorMessage());
			request.setResponseStackTrace(actionRequest.getActionResponse()
					.getDetail().getStackTrace());
		}
		return request;
	}

}
