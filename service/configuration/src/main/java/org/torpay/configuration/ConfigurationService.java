package org.torpay.configuration;

public interface ConfigurationService {

	public String getValue(String key, String defaultValue) throws ConfigurationException;

	public Boolean getBoolean(String key, Boolean defaultValue, String domain) throws ConfigurationException;

	public Boolean getBoolean(String key, Boolean defaultValue) throws ConfigurationException;
	
	public String getErrorCodeMessage(String status, String locale);

}
