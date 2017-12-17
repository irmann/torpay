package org.torpay.service.core.activity.persistence;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.activities.persistence.StoreActionRequestActivity;
import org.torpay.service.core.activity.controller.ActivityControllException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-core-activity-test.xml" })
public class StoreActionRequestActivityTest {

	@Autowired
	protected PersistenceCoreActivityService persistenceCoreActivityService;

	@Test
	public void testSaveActionrequestSuccessful() throws ActivityControllException,
			PersistenceException {
		StoreActionRequestActivity storeActionRequestActivity = new StoreActionRequestActivity();
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action1");
		actionRequest.setProvider("provider1");
		actionRequest.setMerchant("merchant1");
		HashMap<String, String> parameter = new HashMap<String, String>();
		parameter.put("paraName1", "paraValue1");
		actionRequest.setParameters(parameter);
		actionRequest.setRequest_content("content1");
		actionRequest.setRequest_string("string1");
		actionRequest.setRequest_parameters("param1");
		actionRequest.setPassword("password1");
		actionRequest.setToken("token1");
		actionRequest.setSecurity_code("code1");
		actionRequest.setPayment_number("number1");
		actionRequest.setRequest_id("id1");
		actionRequest.setExternalReference1("ex1");
		actionRequest.setExternalReference2("ex2");
		actionRequest.setExternalReference3("ex3");
		actionRequest.appendLog("log");
		actionRequest.setRequest_type("request_type");
		actionRequest.setActionResponse(new ActionResponse());
		actionRequest.getActionResponse().setErrorCode(123);
		actionRequest.getActionResponse().setMessage("message");
		actionRequest.getActionResponse().setStatus("status");
		actionRequest.getActionResponse().setEntranceReference(123456L);
		actionRequest.getActionResponse().getDetail()
				.setTechnicalErrorMessage("technicalErrorMessage");
		actionRequest.getActionResponse().getDetail()
				.setStackTrace("stackTrace");
		actionRequest.setTraceNumber("traceNumber");
		actionRequest.setTransactionCode("transactionCode");

		storeActionRequestActivity.execute(actionRequest);
		Assert.assertNotNull(actionRequest.getRequest_db_id());
		Request request = persistenceCoreActivityService
				.findRequestById(actionRequest.getRequest_db_id());
		Assert.assertEquals(request.getAction(), actionRequest.getAction());
		Assert.assertEquals(request.getProvider(), actionRequest.getProvider());

		Assert.assertEquals(request.getMerchant(), actionRequest.getMerchant());
		Assert.assertNotNull(request.getParameters());
		Assert.assertEquals(request.getRequestContent(),
				actionRequest.getRequest_content());
		Assert.assertEquals(request.getRequestString(),
				actionRequest.getRequest_string());
		Assert.assertEquals(request.getRequestParameters(),
				actionRequest.getRequest_parameters());
		Assert.assertEquals(request.getPassword(), actionRequest.getPassword());
		Assert.assertEquals(request.getToken(), actionRequest.getToken());
		Assert.assertEquals(request.getSecurityCode(),
				actionRequest.getSecurity_code());
		Assert.assertEquals(request.getPaymentNumber(),
				actionRequest.getPayment_number());
		Assert.assertEquals(request.getClientRequestId(),
				actionRequest.getRequest_id());
		Assert.assertEquals(request.getExternalReference1(),
				actionRequest.getExternalReference1());
		Assert.assertEquals(request.getExternalReference2(),
				actionRequest.getExternalReference2());
		Assert.assertEquals(request.getExternalReference3(),
				actionRequest.getExternalReference3());
		Assert.assertEquals(request.getCreator(), null);
		Assert.assertEquals(request.getResponseErrorCode(), actionRequest
				.getActionResponse().getErrorCode().toString());
		Assert.assertEquals(request.getResponseStatus(), actionRequest
				.getActionResponse().getStatus());
		Assert.assertEquals(request.getResponseMessage(), actionRequest
				.getActionResponse().getMessage());
		Assert.assertEquals(request.getResponseEntranceReference(),
				actionRequest.getActionResponse().getEntranceReference()
						.toString());
		Assert.assertEquals(request.getResponseTechnicalErrorMessage(),
				actionRequest.getActionResponse().getDetail()
						.getTechnicalErrorMessage());
		Assert.assertEquals(request.getResponseStackTrace(), actionRequest
				.getActionResponse().getDetail().getStackTrace());
		Assert.assertNotNull(request.getCreationDate());
		Assert.assertEquals(request.getTraceNumber(),
				actionRequest.getTraceNumber());
		Assert.assertEquals(request.getTransactionCode(),
				actionRequest.getTransactionCode());
		Assert.assertEquals(request.getType(), actionRequest.getRequest_type());
		// test update
		actionRequest.setAction("action2");
		storeActionRequestActivity.execute(actionRequest);
		Request request2 = persistenceCoreActivityService
				.findRequestById(actionRequest.getRequest_db_id());
		Assert.assertEquals(request2.getAction(), actionRequest.getAction());

	}
}
