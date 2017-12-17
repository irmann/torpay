package org.torpay.service.core.activity.controller;

import java.util.List;

public interface ActivityFlow {
	public List<ActivityDefinition> getMandatoryActivityNames();

	public List<ActivityDefinition> getCustomActivityNames(String action,
			String provider) throws ActivityControllException;

	public List<ActivityDefinition> getMandatoryActivityNames(String flowName);

	public List<ActivityDefinition> getCustomActivityNames(String flowName,
			String action, String provider) throws ActivityControllException;

	public void addFlow(String flowName, List<ActivityDefinition> list)
			throws ActivityControllException;

}