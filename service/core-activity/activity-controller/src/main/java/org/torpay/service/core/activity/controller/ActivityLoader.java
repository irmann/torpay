package org.torpay.service.core.activity.controller;

import java.util.List;

public interface ActivityLoader {
	public List<CoreActivity> loadActivities() throws ActivityControllException;
}
