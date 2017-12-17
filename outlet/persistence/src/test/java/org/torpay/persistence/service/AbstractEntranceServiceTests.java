package org.torpay.persistence.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.model.RequestLog;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.service.PersistenceEntranceService;
import org.torpay.persistence.service.test.util.CoreActivityServiceSpringDataJpaUtilTest;

public abstract class AbstractEntranceServiceTests {

	@Autowired
	protected PersistenceEntranceService entranceService;

	@Autowired
	protected PersistenceCoreActivityService persistenceCoreActivityService;

	CoreActivityServiceSpringDataJpaUtilTest coreActivityServiceSpringDataJpaUtilTest;

	@Before
	public void setUp() {
		coreActivityServiceSpringDataJpaUtilTest = new CoreActivityServiceSpringDataJpaUtilTest(
				persistenceCoreActivityService);
	}

	@Test
	@Transactional
	public void insertRequestLog() throws PersistenceException {
		RequestLog requestLog = new RequestLog();
		requestLog.setId(10L);
		requestLog.setRequest("test request");
		requestLog.setResponse("test request");
		requestLog.setCreationDate(new DateTime(new Date()));

		this.entranceService.saveRequestLog(requestLog);
		Collection<RequestLog> requestLogs = this.entranceService
				.findRequestLogs();
		for (RequestLog requestLog2 : requestLogs)
			System.out.println(requestLog2.getRequest() + "");
	}

	@Test
	@Transactional
	public void saveRequest() throws PersistenceException {
		Request request = coreActivityServiceSpringDataJpaUtilTest
				.createRequest();
		coreActivityServiceSpringDataJpaUtilTest.saveRequest(request);
		Request request2 = coreActivityServiceSpringDataJpaUtilTest
				.findRequest(request);
		coreActivityServiceSpringDataJpaUtilTest.assertRequest(request,
				request2);
	}

	@Test
	@Transactional
	public void updateRequest() throws PersistenceException {
		Request request = coreActivityServiceSpringDataJpaUtilTest
				.createRequest();
		coreActivityServiceSpringDataJpaUtilTest.saveRequest(request);
		Request request2 = coreActivityServiceSpringDataJpaUtilTest
				.findRequest(request);
		Request request3 = coreActivityServiceSpringDataJpaUtilTest
				.manipulate(request2);
		coreActivityServiceSpringDataJpaUtilTest.saveRequest(request3);
		request3 = coreActivityServiceSpringDataJpaUtilTest
				.findRequest(request3);
		coreActivityServiceSpringDataJpaUtilTest.assertRequest(request2,
				request3);
	}

	@Test
	@Transactional
	public void saveRequestParameter() throws PersistenceException {
		RequestParameter requestParameter = coreActivityServiceSpringDataJpaUtilTest
				.createRequestParameter();
		coreActivityServiceSpringDataJpaUtilTest.saveRequestParameter(requestParameter);
		RequestParameter requestParameter2 = coreActivityServiceSpringDataJpaUtilTest
				.findRequestParameterById(requestParameter);
		coreActivityServiceSpringDataJpaUtilTest.assertRequestParameter(requestParameter,
				requestParameter2);
		RequestParameter requestParameter3 = coreActivityServiceSpringDataJpaUtilTest
				.findRequestParameterByName(requestParameter);
		coreActivityServiceSpringDataJpaUtilTest.assertRequestParameter(requestParameter,
				requestParameter3);
		RequestParameter requestParameter4 = coreActivityServiceSpringDataJpaUtilTest
				.findRequestParameterByParent(requestParameter);
		coreActivityServiceSpringDataJpaUtilTest.assertRequestParameter(requestParameter,
				requestParameter4);
		List<RequestParameter> requestParameter5List = coreActivityServiceSpringDataJpaUtilTest
				.findRequestParameterByRequestId(requestParameter.getRequestId());
		coreActivityServiceSpringDataJpaUtilTest.assertRequestParameter(requestParameter,
				requestParameter5List.get(0));
		
	}
}
