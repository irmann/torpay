package org.torpay.configuration;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.torpay.common.util.Constants;

@Component("configurationService")
public class HardCodeConfigurationService implements ConfigurationService {
	static final Logger LOG = LoggerFactory
			.getLogger(HardCodeConfigurationService.class);
	private static final String ERROR_CODE_MESSAGE_PREFIX = "ERROR_CODE_MESSAGE";
	private static HashMap<String, String> configurations = new HashMap<String, String>();
	static {
		configurations.put(ConfigurationKeys.SERVICE_NAME,
				Constants.SERVICE_NAME);
		configurations.put(
				ConfigurationKeys.ENTRANCE_CONVERTOR_STACK_TRACE_OUTPUT_ENABLE,
				"true");
		configurations.put(ConfigurationKeys.STORE_REQUEST_LOG, "false");
		configurations
				.put(ConfigurationKeys.ENTRANCE_CONVERTOR_ROOT_CAUSED_STACK_TRACE_OUTPUT_ENABLE,
						"true");
		configurations
				.put(ConfigurationKeys.ENTRANCE_CONVERTOR_ROOT_CAUSED_STACK_TRACE_OUTPUT_ONLY_UNKNWON_ERROR_ENABLE,
						"true");

	}

	public String getValue(String key, String defaultValue)
			throws ConfigurationException {
		if (configurations.get(key) == null)
			return defaultValue;
		return configurations.get(key);
	}

	public String getValue(String domain, String key, String defaultValue)
			throws ConfigurationException {
		throw new IllegalStateException("is not implemented");
	}

	public String getString(String domain, String key, String defaultValue)
			throws ConfigurationException {
		throw new IllegalStateException("is not implemented");
	}

	public Boolean getBoolean(String key, Boolean defaultValue)
			throws ConfigurationException {
		if (configurations.get(key) == null)
			return defaultValue;
		try {
			if (configurations.get(key) != null)
				return Boolean.parseBoolean((String) (configurations.get(key)));
		} catch (Exception e) {
			LOG.error("can not find a value for " + key, e);
		}
		return defaultValue;
	}

	public String getErrorCodeMessage(String status, String locale) {
		return configurations.get(ERROR_CODE_MESSAGE_PREFIX + "-" + status);

	}

	@Override
	public Boolean getBoolean(String key, Boolean defaultValue, String domain)
			throws ConfigurationException {
		//if (domain == null)
		return 	getBoolean(key, defaultValue);
		//return null;
	}
}
