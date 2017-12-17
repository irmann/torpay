package org.torpay.common.request;

import java.util.List;

import org.torpay.common.util.Parameter;

public class ProviderRequest {
	private String action;
	private String provider;
	private String reponseMessage;
	private String reponseCode;
	private List<Parameter> values;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public List<Parameter> getValues() {
		return values;
	}

	public void setValues(List<Parameter> values) {
		this.values = values;
	}

	public String getReponseMessage() {
		return reponseMessage;
	}

	public void setReponseMessage(String reponseMessage) {
		this.reponseMessage = reponseMessage;
	}

	public String getReponseCode() {
		return reponseCode;
	}

	public void setReponseCode(String reponseCode) {
		this.reponseCode = reponseCode;
	}
}
