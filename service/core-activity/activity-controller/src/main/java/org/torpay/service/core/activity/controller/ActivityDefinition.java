package org.torpay.service.core.activity.controller;

public class ActivityDefinition {

	private String name;
	private String nextActivity;
	private String fronActivity;

	public ActivityDefinition(String name, String nextActivity,
			String fronActivity) {
		this.name = name;
		this.nextActivity = nextActivity;
		this.fronActivity = fronActivity;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNextActivity() {
		return nextActivity;
	}

	public void setNextActivity(String nextActivity) {
		this.nextActivity = nextActivity;
	}

	public String getFronActivity() {
		return fronActivity;
	}

	public void setFronActivity(String fronActivity) {
		this.fronActivity = fronActivity;
	}

}
