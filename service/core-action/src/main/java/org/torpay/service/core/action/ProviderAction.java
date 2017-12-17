package org.torpay.service.core.action;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.IllegalAddException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.common.exception.ActionException;
import org.torpay.common.interfaces.ActionHandler;
import org.torpay.common.log.LogUtil;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ProviderRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;
import org.torpay.engine.content.EngineContent;
import org.torpay.engine.core.EngineCoreException;
import org.torpay.engine.metadata.EngineMetaData;
import org.torpay.engine.metadata.MetadataException;
import org.torpay.engine.validation.EngineValidation;
import org.torpay.service.core.action.mapping.CustomMapping;
import org.torpay.service.proxy.ProxyEngine;

public class ProviderAction extends ActionBase implements ActionHandler {

	static final Logger LOG = LoggerFactory.getLogger(ProviderAction.class);

	@Override
	public void execute(ActionRequest actionRequest) throws ActionException {
		LogUtil.logTrace(LOG, "start executing activity ", actionRequest);
		extractMapConfig(actionRequest);
		ProviderRequest providerRequest = createProviderRequest(actionRequest);
		storeProviderRequest(providerRequest);
		try {
			sendToEngine(providerRequest);
		} catch (EngineCoreException e) {
			throw new ActionException(e.getErrorCode(), e.getMessage(),
					e.getActivityName(), e);
		}
		storeResult(providerRequest);
		LogUtil.logTrace(LOG, "end executing activity ", actionRequest);
	}

	private void extractMapConfig(ActionRequest actionRequest)
			throws ActionException {

		try {
			String isMapped = ProxyEngine.getEngineMetaData().extractIsMapped(
					actionRequest.getProvider(), actionRequest.getAction());
			String mappedProvider = null;
			String mappedAction = null;
			String customMappedClass = null;
			List<Parameter> mappedValues = null;
			if (isMapped != null && isMapped.equals("true")) {
				actionRequest.setIsMapped(true);
				LogUtil.logDebug(LOG, "request is mapped", actionRequest);
			} else {
				LogUtil.logDebug(LOG, "request is not mapped", actionRequest);
				actionRequest.setIsMapped(false);
			}
			if (hasChannel(actionRequest.getValues())) {
				LogUtil.logDebug(LOG, "request has channel", actionRequest);
				String channel = getChannel(actionRequest.getValues());
				actionRequest.setChannel(channel);
				if (LOG.isDebugEnabled())
					LogUtil.logDebug(LOG, "channel is " + channel,
							actionRequest);

				mappedProvider = ProxyEngine.getEngineMetaData()
						.extractChannelMappedProvider(
								actionRequest.getProvider(),
								actionRequest.getAction(), channel);
				mappedAction = ProxyEngine.getEngineMetaData()
						.extractChannelMappedAction(
								actionRequest.getProvider(),
								actionRequest.getAction(), channel);
				mappedValues = extractChannelMappedPrameters(
						actionRequest.getProvider(), actionRequest.getAction(),
						actionRequest.getValues(), channel);
				customMappedClass = ProxyEngine.getEngineMetaData()
						.extractCustomMappedClass(actionRequest.getProvider(),
								actionRequest.getAction());
			} else if (actionRequest.getIsMapped()) {
				mappedProvider = ProxyEngine.getEngineMetaData()
						.extractMappedProvider(actionRequest.getProvider(),
								actionRequest.getAction());
				mappedAction = ProxyEngine.getEngineMetaData()
						.extractMappedAction(actionRequest.getProvider(),
								actionRequest.getAction());
				mappedValues = extractMappedPrameters(
						actionRequest.getProvider(), actionRequest.getAction(),
						actionRequest.getValues(), null);
				customMappedClass = ProxyEngine.getEngineMetaData()
						.extractCustomMappedClass(actionRequest.getProvider(),
								actionRequest.getAction());
			}
			if (hasChannel(actionRequest.getValues())
					|| actionRequest.getIsMapped()) {
				if (mappedProvider == null)
					throw new ActionException(ErrorCodes.TECHNICAL_ERROR,
							"mapped provider is null in metadata",
							"ProviderAction");
				if (mappedAction == null)
					throw new ActionException(ErrorCodes.TECHNICAL_ERROR,
							"mapped action is null in metadata",
							"ProviderAction");
				if (mappedValues == null || mappedValues.size() == 0)
					throw new ActionException(ErrorCodes.TECHNICAL_ERROR,
							"mapped parameters are null in metadata",
							"ProviderAction");
				actionRequest.setMappedProvider(mappedProvider);
				actionRequest.setMappedAction(mappedAction);
				actionRequest.setMappedValues(mappedValues);
				if (customMappedClass != null) {
					LogUtil.logDebug(LOG, "executing customMappedClass",
							actionRequest);
					executeCustomMappedClass(customMappedClass, actionRequest);
				}
			}
		} catch (MetadataException me) {
			throw new ActionException(me.getErrorCode(), me.getMessage(),
					me.getActivityName(), me);
		}

	}

	private List<Parameter> extractChannelMappedPrameters(String provider,
			String action, List<Parameter> values, String channel)
			throws ActionException, MetadataException {

		return extractMappedPrameters(provider, action, values, channel);
	}

	private String getChannel(List<Parameter> values) {
		// TODO implement me
		throw new IllegalArgumentException("this methos is not impelemented");
	}

	private boolean hasChannel(List<Parameter> values) {
		// TODO implement me
		return false;
	}

	private List<Parameter> extractMappedPrameters(String provider,
			String action, List<Parameter> values, String channel)
			throws MetadataException, ActionException {
		List<Parameter> list = new ArrayList<Parameter>();
		for (Parameter parameter : values) {
			Parameter cparameter = copy(parameter);
			String name = null;
			if (channel == null)
				name = ProxyEngine.getEngineMetaData().extractMappedParameter(
						provider, action, cparameter.getName());
			else
				name = ProxyEngine.getEngineMetaData()
						.extractChannelMappedParameter(provider, action,
								cparameter.getName(), channel);
			if (name == null)
				throw new ActionException(ErrorCodes.TECHNICAL_ERROR,
						"mapped parameter is null in metadata,<"
								+ parameter.getName() + ">", "ProviderAction");
			cparameter.setName(name);
			if (parameter.getObjectValue() != null) {
				List<Parameter> ovlist = extractMappedPrameters(provider,
						action, parameter.getObjectValue(), channel);
				cparameter.setObjectValue(ovlist);
			}
			if (parameter.getArrayValue() != null
					&& parameter.getArrayValue().length > 0) {
				List[] avList = new ArrayList[parameter.getArrayValue().length];
				int i = 0;
				for (List alist : parameter.getArrayValue()) {
					avList[i] = extractMappedPrameters(provider, action, alist,
							channel);
				}
				cparameter.setArrayValue(avList);
			}
			list.add(parameter);
		}

		return list;
	}

	private Parameter copy(Parameter parameter) {
		Parameter cpParameter = new Parameter();
		cpParameter.setName(parameter.getName());
		cpParameter.setType(parameter.getType());
		cpParameter.setValue(parameter.getValue());
		return cpParameter;
	}

	private void executeCustomMappedClass(String customMappedClass,
			ActionRequest actionRequest) throws ActionException {
		CustomMapping customMapping = createCustomMapper(customMappedClass);
		customMapping.convert(actionRequest);
	}

	private CustomMapping createCustomMapper(String customMappedClass) {
		Class c = null;
		try {
			c = Class.forName(customMappedClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		CustomMapping customMapping = null;
		try {
			customMapping = (CustomMapping) c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return customMapping;
	}

	private void storeResult(ProviderRequest providerRequest) {
		// TODO implement me

	}

	private void sendToEngine(ProviderRequest providerRequest)
			throws EngineCoreException {
		ProxyEngine.getEngineCore().process(providerRequest);

	}

	private void storeProviderRequest(ProviderRequest providerRequest) {
		// TODO implement me

	}

	private ProviderRequest createProviderRequest(ActionRequest actionRequest) {
		ProviderRequest providerRequest = new ProviderRequest();
		if (actionRequest.getIsMapped() || actionRequest.getChannel() != null) {
			providerRequest.setProvider(actionRequest.getMappedProvider());
			providerRequest.setAction(actionRequest.getMappedAction());
			providerRequest.setValues(actionRequest.getMappedValues());

		} else {
			providerRequest.setProvider(actionRequest.getProvider());
			providerRequest.setAction(actionRequest.getAction());
			providerRequest.setValues(actionRequest.getValues());

		}

		return providerRequest;
	}

}
