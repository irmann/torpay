package org.torpay.service.core.activity.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.util.ErrorCodes;
import org.torpay.service.core.activity.controller.ActivityController;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityErrorHandlerDefaultImpl;
import org.torpay.service.core.activity.controller.test.AC10CoreActivity;
import org.torpay.service.core.activity.controller.test.AC11CoreActivity;
import org.torpay.service.core.activity.controller.test.AC12CoreActivity;
import org.torpay.service.core.activity.controller.test.AC13CoreActivity;
import org.torpay.service.core.activity.controller.test.AC14CoreActivity;
import org.torpay.service.core.activity.controller.test.AC15CoreActivity;
import org.torpay.service.core.activity.controller.test.AC16CoreActivity;
import org.torpay.service.core.activity.controller.test.AC17CoreActivity;
import org.torpay.service.core.activity.controller.test.AC1CoreActivity;
import org.torpay.service.core.activity.controller.test.AC2CoreActivity;
import org.torpay.service.core.activity.controller.test.AC3CoreActivity;
import org.torpay.service.core.activity.controller.test.AC4CoreActivity;
import org.torpay.service.core.activity.controller.test.AC5CoreActivity;
import org.torpay.service.core.activity.controller.test.AC6CoreActivity;
import org.torpay.service.core.activity.controller.test.AC7CoreActivity;
import org.torpay.service.core.activity.controller.test.AC8CoreActivity;
import org.torpay.service.core.activity.controller.test.AC9CoreActivity;
import org.torpay.service.core.activity.controller.test.InitCoreActivity;
import org.torpay.service.core.activity.controller.test2.AC18CoreActivity;

@ContextConfiguration(locations = { "classpath:spring/service-core-activity-controller-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivityControllerTest {
	static final Logger LOG = LoggerFactory.getLogger(ActivityControllerTest.class);
	@Autowired
	private ActivityController activityBase;

	@Before
	public void setup() {
	}

	@Test
	public void testProcessMandatoryActivity() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertTrue(InitCoreActivity.VISITED);
		Assert.assertTrue(AC1CoreActivity.VISITED);
		Assert.assertTrue(AC2CoreActivity.VISITED);
		Assert.assertTrue(AC3CoreActivity.VISITED);
		Assert.assertTrue(AC4CoreActivity.VISITED);
	}

	@Test
	public void testProcessMandatoryActivityFailed() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertTrue(InitCoreActivity.VISITED);
		Assert.assertTrue(AC1CoreActivity.VISITED);
		Assert.assertTrue(AC2CoreActivity.VISITED);
		Assert.assertTrue(AC3CoreActivity.VISITED);
		Assert.assertTrue(AC4CoreActivity.VISITED);
		Assert.assertFalse(AC8CoreActivity.VISITED);
		Assert.assertTrue(AC10CoreActivity.VISITED);
	}

	@Test
	public void testProcessCustomActivity() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertTrue(InitCoreActivity.VISITED);
		Assert.assertTrue(AC1CoreActivity.VISITED);
		Assert.assertTrue(AC7CoreActivity.VISITED);
		Assert.assertTrue(AC5CoreActivity.VISITED);
		Assert.assertTrue(AC6CoreActivity.VISITED);
		Assert.assertTrue(AC2CoreActivity.VISITED);
		Assert.assertTrue(AC3CoreActivity.VISITED);
		Assert.assertTrue(AC4CoreActivity.VISITED);
	}

	@Test
	public void testIsMandatoryActivity() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		AC9CoreActivity.MANDATORY = true;
		activityBase.process(actionRequest, null);
		Assert.assertTrue(AC9CoreActivity.VISITED);
		reset();
		AC9CoreActivity.MANDATORY = false;
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC9CoreActivity.VISITED);
	}

	@Test
	public void testActivityConditionHandler() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC11CoreActivity.VISITED);

	}

	@Test
	public void testActivityIsActive() throws Exception {
		reset();
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC12CoreActivity.VISITED);

	}

	@Test
	public void testBlacklist1() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list2;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action1");
		actionRequest.setProvider("provider1");
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC14CoreActivity.VISITED);
	}

	@Test
	public void testBlacklist2() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list2;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action1");
		actionRequest.setProvider("provider1");
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC15CoreActivity.VISITED);
	}

	@Test
	public void testWhitelist1() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list2;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action1");
		actionRequest.setProvider("provider1");
		activityBase.process(actionRequest, null);
		Assert.assertTrue(AC16CoreActivity.VISITED);
	}

	@Test
	public void testWhitelist2() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list2;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action6");
		actionRequest.setProvider("provider1");
		activityBase.process(actionRequest, null);
		Assert.assertFalse(AC16CoreActivity.VISITED);
	}

	@Test
	public void testWhitelist3() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list2;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setAction("action1");
		actionRequest.setProvider("provider1");
		activityBase.process(actionRequest, null);
		Assert.assertTrue(AC17CoreActivity.VISITED);
	}

	@Test
	public void testBlacklistAndWhiltelist() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list5;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		ActionResponse actionResponse = new ActionResponse();
		try {
			activityBase.process(actionRequest, actionResponse);
		} catch (ActivityControllException abe) {
			Assert.assertEquals(abe.getErrorCode().getCode().intValue(),
					ErrorCodes.TECHNICAL_ERROR.getCode().intValue());
		} catch (Exception e) {
			throw e;
		}

	}

	@Test
	public void testMultipath() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list6;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		activityBase.process(actionRequest, null);
		Assert.assertTrue(AC18CoreActivity.VISITED);
	}

	@Test
	public void testActivityErrorHandler() throws Exception {
		reset();
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list7;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list4;
		ActionRequest actionRequest = new ActionRequest();
		try {
			activityBase.process(actionRequest, null);
		} catch (ActivityControllException e) {
			Assert.assertTrue(ActivityErrorHandlerDefaultImpl.VISITED);
		} catch (Exception e) {
			throw e;
		}
	}

	private void reset() {
		InitCoreActivity.VISITED = false;
		AC1CoreActivity.VISITED = false;
		AC2CoreActivity.VISITED = false;
		AC3CoreActivity.VISITED = false;
		AC4CoreActivity.VISITED = false;
		AC5CoreActivity.VISITED = false;
		AC6CoreActivity.VISITED = false;
		AC7CoreActivity.VISITED = false;
		AC8CoreActivity.VISITED = false;
		AC9CoreActivity.VISITED = false;
		AC9CoreActivity.MANDATORY = false;
		AC11CoreActivity.VISITED = false;
		AC12CoreActivity.VISITED = false;
		AC13CoreActivity.VISITED = false;
		AC14CoreActivity.VISITED = false;
		AC16CoreActivity.VISITED = false;
		AC17CoreActivity.VISITED = false;
		AC18CoreActivity.VISITED = false;
		InitCoreActivity.NEXT = CoreActivityConstantTest.AC_1;
		ActivityFlowTestImpl.mList = ActivityFlowTestImpl.list1;
		ActivityFlowTestImpl.cList = ActivityFlowTestImpl.list3;
		ActivityErrorHandlerDefaultImpl.VISITED = false;

	}
}
