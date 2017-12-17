package org.torpay.persistence.service.test.util;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.transaction.annotation.Transactional;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.persistence.service.PersistenceException;

public class CoreActivityServiceSpringDataJpaUtilTest {

	private PersistenceCoreActivityService persistenceCoreActivityService;
	private Request request;

	public CoreActivityServiceSpringDataJpaUtilTest(
			PersistenceCoreActivityService persistenceCoreActivityService) {
		this.persistenceCoreActivityService = persistenceCoreActivityService;
	}

	public void assertRequest(Request request, Request request2) {
		Assert.assertEquals(request.getAction(), request2.getAction());
		Assert.assertEquals(request.getProvider(), request2.getProvider());
		Assert.assertEquals(request.getMerchant(), request2.getMerchant());
		Assert.assertEquals(request.getParameters(), request2.getParameters());
		Assert.assertEquals(request.getRequestContent(),
				request2.getRequestContent());
		Assert.assertEquals(request.getRequestString(),
				request2.getRequestString());
		Assert.assertEquals(request.getRequestParameters(),
				request2.getRequestParameters());
		Assert.assertEquals(request.getPassword(), request2.getPassword());
		Assert.assertEquals(request.getToken(), request2.getToken());
		Assert.assertEquals(request.getSecurityCode(),
				request2.getSecurityCode());
		Assert.assertEquals(request.getPaymentNumber(),
				request2.getPaymentNumber());
		Assert.assertEquals(request.getClientRequestId(),
				request2.getClientRequestId());
		Assert.assertEquals(request.getExternalReference1(),
				request2.getExternalReference1());
		Assert.assertEquals(request.getExternalReference2(),
				request2.getExternalReference2());
		Assert.assertEquals(request.getExternalReference3(),
				request2.getExternalReference3());
		Assert.assertEquals(request.getLog(), request2.getLog());
		Assert.assertEquals(request.getCreator(), request2.getCreator());
		Assert.assertEquals(request.getResponseErrorCode(),
				request2.getResponseErrorCode());
		Assert.assertEquals(request.getResponseStatus(),
				request2.getResponseStatus());
		Assert.assertEquals(request.getResponseMessage(),
				request2.getResponseMessage());
		Assert.assertEquals(request.getResponseEntranceReference(),
				request2.getResponseEntranceReference());
		Assert.assertEquals(request.getResponseTechnicalErrorMessage(),
				request2.getResponseTechnicalErrorMessage());
		Assert.assertEquals(request.getResponseStatus(),
				request2.getResponseStatus());
		Assert.assertNotNull(request2.getCreationDate());
		Assert.assertEquals(request.getTraceNumber(), request2.getTraceNumber());
		Assert.assertEquals(request.getTransactionCode(),
				request2.getTransactionCode());
		Assert.assertEquals(request.getType(), request2.getType());

	}

	@Transactional
	public void saveRequest(Request request) throws PersistenceException {
		this.persistenceCoreActivityService.saveRequest(request);
		setRequest(request);
	}

	public Request findRequest(Request request) throws PersistenceException {
		Request request2 = this.persistenceCoreActivityService
				.findRequestById(request.getId());
		setRequest(request2);
		return request2;
	}

	public Request createRequest() {
		Request request = new Request();
		request.setAction("action1");
		request.setProvider("provider1");
		request.setMerchant("merchant1");
		request.setParameters("Parameters");
		request.setRequestContent("Request_content");
		request.setRequestString("request_string");
		request.setRequestParameters("request_parameters");
		request.setPassword("password");
		request.setToken("token");
		request.setSecurityCode("Security_code");
		request.setPaymentNumber("payment_number");
		request.setClientRequestId("request_id");
		request.setExternalReference1("externalReference1");
		request.setExternalReference2("externalReference2");
		request.setExternalReference3("externalReference3");
		request.setLog("log");
		request.setCreator("creator");
		request.setCreationDate(new DateTime(new Date()));
		request.setResponseErrorCode("ErrorCode");
		request.setResponseStatus("Status");
		request.setResponseMessage("Message");
		request.setResponseEntranceReference("Reference");
		request.setResponseTechnicalErrorMessage("TechnicalErrorMessage");
		request.setResponseStackTrace("StackTrace");
		request.setTraceNumber("traceNumber");
		request.setTransactionCode("transactionCode");
		request.setType("type");
		setRequest(request);
		return request;

	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Request manipulate(Request request2) {
		request2.setAction("action2");
		request2.setAction("action2");
		request2.setProvider("provider2");
		request2.setMerchant("merchant3");
		request2.setParameters("Parameters2");
		request2.setRequestContent("Request_content2");
		request2.setRequestString("request_string2");
		request2.setRequestParameters("request_parameters2");
		request2.setPassword("password2");
		request2.setToken("token2");
		request2.setSecurityCode("Security_code2");
		request2.setPaymentNumber("payment_number2");
		request2.setClientRequestId("request_id2");
		request2.setExternalReference1("externalReference1_2");
		request2.setExternalReference2("externalReference2_2");
		request2.setExternalReference3("externalReference3_2");
		request2.setLog("log2");
		request2.setCreator("creator2");
		request2.setCreationDate(new DateTime(new Date()));
		request2.setResponseErrorCode("ErrorCode2");
		request2.setResponseStatus("Status2");
		request2.setResponseMessage("Message2");
		request2.setResponseEntranceReference("Reference2");
		request2.setResponseTechnicalErrorMessage("TechnicalErrorMessage2");
		request2.setResponseStackTrace("StackTrace2");
		request2.setTraceNumber("traceNumber2");
		request2.setTransactionCode("transactionCode2");
		request2.setType("type2");
		setRequest(request2);
		return request2;
	}

	public RequestParameter createRequestParameter() {
		RequestParameter requestParameter = new RequestParameter();
		requestParameter.setCreationDate(new DateTime(new Date()));
		requestParameter.setModifiedDate(new DateTime(new Date()));
		requestParameter.setName("name");
		requestParameter.setValue("value");
		requestParameter.setType("type");
		requestParameter.setRequestId(1L);
		requestParameter.setParent("parent");
		requestParameter.setCreator("creator");
		requestParameter.setModifiedBy("modifiedBy");
		return requestParameter;
	}

	public void saveRequestParameter(RequestParameter requestParameter)
			throws PersistenceException {
		this.persistenceCoreActivityService
				.saveRequestParameter(requestParameter);

	}

	public void assertRequestParameter(RequestParameter requestParameter,
			RequestParameter requestParameter2) {

		Assert.assertEquals(requestParameter.getType(),
				requestParameter2.getType());
		Assert.assertEquals(requestParameter.getId(), requestParameter2.getId());
		Assert.assertEquals(requestParameter.getName(),
				requestParameter2.getName());
		Assert.assertEquals(requestParameter.getValue(),
				requestParameter2.getValue());
		Assert.assertEquals(requestParameter.getCreator(),
				requestParameter2.getCreator());
		Assert.assertEquals(requestParameter.getModifiedBy(),
				requestParameter2.getModifiedBy());
		Assert.assertEquals(requestParameter.getRequestId(),
				requestParameter2.getRequestId());
		Assert.assertEquals(requestParameter.getParent(),
				requestParameter2.getParent());
		Assert.assertNotNull(requestParameter2.getCreationDate());
		Assert.assertNotNull(requestParameter2.getModifiedDate());

	}

	public RequestParameter findRequestParameterById(
			RequestParameter requestParameter) throws PersistenceException {
		return this.persistenceCoreActivityService
				.findRequestParameterById(requestParameter.getId());
	}

	public RequestParameter findRequestParameterByName(
			RequestParameter requestParameter) throws PersistenceException {
		return this.persistenceCoreActivityService
				.findRequestParameterByName(requestParameter.getName());
	}

	public RequestParameter findRequestParameterByParent(
			RequestParameter requestParameter) throws PersistenceException {
		return this.persistenceCoreActivityService
				.findRequestParameterByParent(requestParameter.getParent());
	}

	public List<RequestParameter> findRequestParameterByRequestId(Long requestId)
			throws PersistenceException {
		return this.persistenceCoreActivityService
				.findRequestParameterByRequestId(requestId);
	}

}
