package org.torpay.common.util;

public enum Status {
	SUCCESS("SUCCESS"),FAILED("FAILED");

	private String status;

	private Status(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
