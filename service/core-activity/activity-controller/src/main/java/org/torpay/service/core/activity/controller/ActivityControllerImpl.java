package org.torpay.service.core.activity.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Status;
@Component
public class ActivityControllerImpl implements ActivityController {

	static final Logger LOG = LoggerFactory.getLogger(ActivityControllerImpl.class);

	@Autowired
	private ActivityFlow activityFlow;

	@Autowired
	private ActivityLoader activityLoader;

	public ActivityControllerImpl() {
	}

	public void process(ActionRequest actionRequest,
			ActionResponse actionResponse) throws ActivityControllException {
		List<CoreActivity> activities = loadActivities(actionRequest);
		activities = orderActivities(activities);
		for (CoreActivity activity : activities)
			try {
				activity.execute(actionRequest);
			} catch (ActivityControllException se) {
				if (activity.getActivityErrorHandler() != null)
					activity.getActivityErrorHandler().handelError(
							actionRequest, se);
				else
					LOG.warn("activity " + activity.getName()
							+ " has not any ActivityErrorHandler");
				throw se;
			}
	}

	private ActivityControllException makeServiceExceptionForUnknownError(Throwable th) {
		return new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
				"internal error during request process " + th.getMessage(),
				"ServiceCore", th);
	}

	private void makeServiceExceptionByMsg(String msg) throws ActivityControllException {
		throw new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
				"internal error during request process:" + msg, "ServiceCore");
	}

	private List<CoreActivity> orderActivities(List<CoreActivity> activities)
			throws ActivityControllException {
		CoreActivity currentActivity = getActivityByName(activities,
				CoreActivityNames.INIT);
		if (currentActivity == null)
			makeServiceExceptionByMsg("init core activity is null");
		List<CoreActivity> list = new ArrayList<CoreActivity>();
		do {
			list.add(currentActivity);
			addFronCoreActivityDependActivities(currentActivity.getName(),
					list, activities);
			currentActivity = getActivityByName(activities,
					currentActivity.nextCoreActivity());
		} while (currentActivity != null);
		return list;
	}

	private void addFronCoreActivityDependActivities(String name,
			List<CoreActivity> list, List<CoreActivity> activities) {
		if (name != null) {
			List<CoreActivity> fronCoreActivityDependActivities = getActivitiesByFronActivity(
					activities, name);
			list.addAll(sortByOrder(fronCoreActivityDependActivities));
		}

	}

	private Collection<? extends CoreActivity> sortByOrder(
			List<CoreActivity> fronCoreActivityDependActivities) {
		Collections.sort(fronCoreActivityDependActivities,
				Collections.reverseOrder());
		return fronCoreActivityDependActivities;
	}

	private CoreActivity getActivityByOrder(List<CoreActivity> activities,
			Integer order) {
		if (order != null)
			for (CoreActivity coreActivity : activities)
				if (coreActivity.getExpectedExecutionOrder() != null
						&& coreActivity.getExpectedExecutionOrder().intValue() == order
								.intValue())
					return coreActivity;
		return null;
	}

	private CoreActivity getActivityByName(List<CoreActivity> activities,
			String name) {
		if (name != null)
			for (CoreActivity coreActivity : activities)
				if (coreActivity.getName() != null
						&& coreActivity.getName().equals(name))
					return coreActivity;
		return null;
	}

	private List<CoreActivity> getActivitiesByFronActivity(
			List<CoreActivity> activities, String name) {
		List<CoreActivity> list = new ArrayList<CoreActivity>();
		if (name != null)
			for (CoreActivity coreActivity : activities)
				if (coreActivity.getFrontActivity() != null
						&& coreActivity.getFrontActivity().equals(name))
					list.add(coreActivity);
		return list;
	}

	private List<CoreActivity> loadActivities(ActionRequest actionRequest)
			throws ActivityControllException {
		List<CoreActivity> activities = loadMandatoryActivities(actionRequest);
		List<CoreActivity> customActivities = loadCustomActivities(actionRequest);
		activities.addAll(customActivities);
		return activities;
	}

	private List<CoreActivity> loadCustomActivities(ActionRequest actionrequest)
			throws ActivityControllException {
		List<ActivityDefinition> customActivitiesName = activityFlow
				.getCustomActivityNames(actionrequest.getFlowName(),actionrequest.getAction(),
						actionrequest.getProvider());
		return loadActivities(customActivitiesName);
	}

	private List<CoreActivity> loadActivities(
			List<ActivityDefinition> activitiesName) throws ActivityControllException {
		List<CoreActivity> list = new ArrayList<CoreActivity>();
		for (ActivityDefinition activityDefinition : activitiesName) {
			for (CoreActivity coreActivity : activityLoader.loadActivities())
				if (activityDefinition.getName().equals(coreActivity.getName())) {
					coreActivity.setNextActivity(activityDefinition
							.getNextActivity());
					coreActivity.setFrontActivity(activityDefinition
							.getFronActivity());
					list.add(coreActivity);
				}
		}
		return list;
	}

	private List<CoreActivity> loadMandatoryActivities(ActionRequest actionrequest)
			throws ActivityControllException {
		List<ActivityDefinition> mandatoryActivitiesName = activityFlow
				.getMandatoryActivityNames(actionrequest.getFlowName());
		return loadActivities(mandatoryActivitiesName);
	}
}
