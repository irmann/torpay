package org.torpay.engine.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.request.ProviderRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/engine-core.xml" })
public class EngineCoreTest {

	@Autowired
	private EngineCore engineCore;

	@Before
	public void setup() {
	}

	@Test
	public void validationFailedProviderNullTest() {
		try {
			ProviderRequest providerRequest = createProviderRequest();
			providerRequest.setProvider(null);
			engineCore.process(providerRequest);
		} catch (EngineCoreException e) {
			Assert.assertEquals(e.getErrorCode().getCode(),
					ErrorCodes.TECHNICAL_ERROR.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void validationFailedActionNullTest() {
		try {
			ProviderRequest providerRequest = createProviderRequest();
			providerRequest.setAction(null);
			engineCore.process(providerRequest);
		} catch (EngineCoreException e) {
			Assert.assertEquals(e.getErrorCode().getCode(),
					ErrorCodes.TECHNICAL_ERROR.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void validationFailedInvalidProviderTest() {
		try {
			ProviderRequest providerRequest = createProviderRequest();
			providerRequest.setProvider("invalid_provider");
			engineCore.process(providerRequest);
		} catch (EngineCoreException e) {
			Assert.assertEquals(e.getErrorCode().getCode(),
					ErrorCodes.TECHNICAL_ERROR.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void validationFailedInvalidActionTest() {
		try {
			ProviderRequest providerRequest = createProviderRequest();
			providerRequest.setAction("invalid_action");
			engineCore.process(providerRequest);
		} catch (EngineCoreException e) {
			Assert.assertEquals(e.getErrorCode().getCode(),
					ErrorCodes.TECHNICAL_ERROR.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void AuthorizeNetAIMSuccessTest() throws EngineCoreException {
		ProviderRequest providerRequest = createProviderRequest();
		engineCore.process(providerRequest);
	}
	
	@Test
	public void AuthorizeNetDPMSuccessTest() throws EngineCoreException {
		ProviderRequest providerRequest = createDPMProviderRequest();
		engineCore.process(providerRequest);
	}

	private ProviderRequest createDPMProviderRequest() {
		ProviderRequest providerRequest = new ProviderRequest();
		providerRequest.setProvider("AuthorizeNet");
		providerRequest.setAction("AuthorizeNetDPM");
		List<Parameter> values = new ArrayList<Parameter>();
		Parameter amount = new Parameter();
		amount.setName("amount");
		amount.setValue("4.99");
		values.add(amount);
		providerRequest.setValues(values);
		return providerRequest;
	}

	private ProviderRequest createProviderRequest() {
		ProviderRequest providerRequest = new ProviderRequest();
		providerRequest.setProvider("AuthorizeNet");
		providerRequest.setAction("AuthorizeNetAIM");
		List<Parameter> values = new ArrayList<Parameter>();
		Parameter amount = new Parameter();
		amount.setName("amount");
		amount.setValue("4.99");
		Parameter cardNum = new Parameter();
		cardNum.setName("card_num");
		cardNum.setValue("411111111111111");
		Parameter expDate = new Parameter();
		expDate.setName("exp_date");
		expDate.setValue("0515");
		values.add(amount);
		values.add(cardNum);
		values.add(expDate);
		providerRequest.setValues(values);
		return providerRequest;
	}
}
