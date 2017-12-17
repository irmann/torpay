package org.torpay.service.core.activity.validation;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.test.JSONSample;
import org.torpay.common.util.Constants;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;
import org.torpay.engine.content.EngineContent;
import org.torpay.service.core.activity.activities.validation.GeneralParametersValidationActivity;
import org.torpay.service.core.activity.controller.ActivityControllException;

@ContextConfiguration(locations = { "classpath:service-core-activity.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GeneralParametersValidationActivityTest {

	@Autowired
	protected EngineContent engineContent;

	@Test
	public void testAll() throws ActivityControllException {
		GeneralParametersValidationActivity activity = new GeneralParametersValidationActivity();
		ActionRequest actionRequest = new ActionRequest();
		actionRequest.setProvider(Constants.PROVIDER_TORPAY_TEST);
		actionRequest.setAction(Constants.ACTION_TORPAY_TEST_1);
		List<Parameter> values = new ArrayList<Parameter>();
		actionRequest.setValues(fillValues(values));
		try {
			// test required:true
			// param1 is required but it is not set in values
			Parameter param1 = getParameter(values, "param1");
			values.remove(param1);
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(
					ErrorCodes.SERVICE_VALIDATION_REQUIRED_PARAMETER_MISSED
							.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test required:true
			// param000 is not required and it is not set in values
			values = reset(actionRequest);
			Parameter param000 = getParameter(values, "param000");
			param000.setValue(null);
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(
					ErrorCodes.SERVICE_VALIDATION_REQUIRED_PARAMETER_VALUE_MISSED
							.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test required:true
			// param1 is required but its value is not set in values
			values = reset(actionRequest);
			Parameter param1 = getParameter(values, "param1");
			param1.setValue(null);
			actionRequest.setValues(fillValues(values));
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(
					ErrorCodes.SERVICE_VALIDATION_REQUIRED_PARAMETER_VALUE_MISSED
							.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test type: number failed
			// param2 is number but it is set to character
			values = reset(actionRequest);
			Parameter param2 = getParameter(values, "param2");
			param2.setValue("nonumber");
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(
					ErrorCodes.SERVICE_VALIDATION_TYPE_NUMBER_EXPECTED
							.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test length failed
			values = reset(actionRequest);
			Parameter name5 = getParameter(values, "param5");
			name5.setValue("1234");
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(ErrorCodes.SERVICE_VALIDATION_LENGTH.getCode(),
					se.getErrorCode().getCode());
		}

		try {
			// test max length failed
			values = reset(actionRequest);
			Parameter name6 = getParameter(values, "param6");
			name6.setValue("123456789");
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(ErrorCodes.SERVICE_VALIDATION_LENGTH_MAX
					.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test min length failed
			values = reset(actionRequest);
			Parameter name7 = getParameter(values, "param7");
			name7.setValue("12");
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(ErrorCodes.SERVICE_VALIDATION_LENGTH_MIN
					.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test custom validator : number failed
			// param8 is number but it is set to character
			values = reset(actionRequest);
			Parameter param8 = getParameter(values, "param8");
			param8.setValue("nonumber");
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.assertEquals(
					ErrorCodes.SERVICE_VALIDATION_TYPE_NUMBER_EXPECTED
							.getCode(), se.getErrorCode().getCode());
		}

		try {
			// test all success
			values = reset(actionRequest);
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.fail();
		}

	}

	private List<Parameter> reset(ActionRequest actionRequest) {
		List<Parameter> values;
		values = new ArrayList<Parameter>();
		actionRequest.setValues(fillValues(values));
		return values;
	}

	private Parameter getParameter(List<Parameter> values, String name) {
		for (Parameter p : values)
			if (p.getName().equals(name))
				return p;
		return null;
	}

	private List<Parameter> fillValues(List<Parameter> values) {
		Parameter param00 = new Parameter();
		param00.setName("param00");
		param00.setValue("1234567890");
		values.add(param00);
		Parameter param000 = new Parameter();
		param000.setName("param000");
		param000.setValue("1234567890");
		values.add(param000);
		Parameter param1 = new Parameter();
		param1.setName("param1");
		param1.setValue("1234567890");
		values.add(param1);
		Parameter param2 = new Parameter();
		param2.setName("param2");
		param2.setValue("1234567890");
		values.add(param2);
		Parameter param3 = new Parameter();
		param3.setName("param3");
		param3.setValue("1234567.90");
		values.add(param3);
		Parameter param4 = new Parameter();
		param4.setName("param4");
		param4.setValue("1234567.90");
		values.add(param4);
		Parameter param5 = new Parameter();
		param5.setName("param5");
		param5.setValue("1234567890");
		values.add(param5);
		Parameter param6 = new Parameter();
		param6.setName("param6");
		param6.setValue("12345");
		values.add(param6);
		Parameter param7 = new Parameter();
		param7.setName("param7");
		param7.setValue("1234");
		values.add(param7);
		Parameter param99 = new Parameter();
		param99.setName("param99");
		param99.setValue("1234567890");
		values.add(param99);
		Parameter param8 = new Parameter();
		param8.setName("param8");
		param8.setValue("1234567890");
		values.add(param8);
		return values;
	}

	@Test
	public void testAll2() throws GlobalExcetion {
		List<Parameter> values = engineContent.extractContent(
				JSONSample.content, "json");
		try {
			GeneralParametersValidationActivity activity = new GeneralParametersValidationActivity();
			ActionRequest actionRequest = new ActionRequest();
			actionRequest.setProvider(Constants.PROVIDER_TORPAY_TEST);
			actionRequest.setAction(Constants.ACTION_TORPAY_TEST_2);
			actionRequest.setValues(values);
			activity.executeChild(actionRequest);
		} catch (ActivityControllException se) {
			Assert.fail();
		}
	}

}
