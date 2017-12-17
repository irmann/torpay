package org.torpay.common.util;

public enum Provider {

	AUTHORIZENET("AUTHORIZENET");

	private String provider;

	private Provider(String s) {
		provider = s;
	}

	public String getProvider() {
		return provider;
	}

}
