package org.torpay.service.core.activity.persistence;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.test.JSONSample;
import org.torpay.common.util.Parameter;
import org.torpay.engine.content.EngineContent;
import org.torpay.engine.content.JSONAssert;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.service.core.activity.activities.persistence.LoadActionRequestParametersActivity;
import org.torpay.service.core.activity.activities.persistence.StoreActionRequestParametersActivity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:service-core-activity-test.xml" })
public class ActionRequestParametersActivitiesTest {

	@Autowired
	protected PersistenceCoreActivityService persistenceCoreActivityService;

	@Autowired
	protected EngineContent engineContent;

	@Test
	public void testSaveActionrequestParametersSuccessful()
			throws GlobalExcetion {
		List<Parameter> values = engineContent.extractContent(
				JSONSample.content, "json");
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setValues(values);
		actionRequest.setRequest_db_id(1L);
		StoreActionRequestParametersActivity storeActionRequestParametersActivity = new StoreActionRequestParametersActivity();
		storeActionRequestParametersActivity.executeChild(actionRequest);
		LoadActionRequestParametersActivity loadActionRequestParametersActivity = new LoadActionRequestParametersActivity();
		ActionRequest actionRequest2 = new ActionRequest();
		actionRequest2.setRequest_db_id(1L);
		loadActionRequestParametersActivity.executeChild(actionRequest2);
		JSONAssert.doAssert(actionRequest2.getValues(), engineContent);
	}

}
