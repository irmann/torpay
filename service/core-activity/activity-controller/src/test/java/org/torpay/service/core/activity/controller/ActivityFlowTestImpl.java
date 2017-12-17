package org.torpay.service.core.activity.controller;

import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_1;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_10;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_11;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_12;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_13;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_14;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_15;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_16;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_17;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_18;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_19;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_2;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_3;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_4;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_5;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_6;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_7;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_8;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.AC_9;
import static org.torpay.service.core.activity.controller.CoreActivityConstantTest.INIT;

import java.util.ArrayList;
import java.util.List;

import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.ActivityDefinition;
import org.torpay.service.core.activity.controller.ActivityFlow;

public class ActivityFlowTestImpl implements ActivityFlow {
	static public List<ActivityDefinition> list1 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list2 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list3 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list4 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list5 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list6 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> list7 = new ArrayList<ActivityDefinition>();
	static public List<ActivityDefinition> mList = list1;
	static public List<ActivityDefinition> cList = list3;
	static {
		list1.add(new ActivityDefinition(INIT, AC_1, null));
		list1.add(new ActivityDefinition(AC_1, AC_2, null));
		list1.add(new ActivityDefinition(AC_2, AC_3, null));
		list1.add(new ActivityDefinition(AC_3, AC_4, null));
		list1.add(new ActivityDefinition(AC_4, AC_9, null));
		list1.add(new ActivityDefinition(AC_8, null, null));
		list1.add(new ActivityDefinition(AC_9, AC_10, null));
		list1.add(new ActivityDefinition(AC_10, AC_11, null));
		list1.add(new ActivityDefinition(AC_12, null, null));

		list2.add(new ActivityDefinition(INIT, AC_14, null));
		list2.add(new ActivityDefinition(AC_14, AC_15, null));
		list2.add(new ActivityDefinition(AC_15, AC_16, null));
		list2.add(new ActivityDefinition(AC_16, AC_17, null));
		list2.add(new ActivityDefinition(AC_17, null, null));

		list5.add(new ActivityDefinition(INIT, AC_13, null));
		list5.add(new ActivityDefinition(AC_13, null, null));

		list6.add(new ActivityDefinition(INIT, AC_18, null));
		list6.add(new ActivityDefinition(AC_18, null, null));

		list7.add(new ActivityDefinition(INIT, AC_19, null));
		list7.add(new ActivityDefinition(AC_19, null, null));

		list3.add(new ActivityDefinition(AC_6, null, AC_1));
		list3.add(new ActivityDefinition(AC_7, null, AC_1));
		list3.add(new ActivityDefinition(AC_5, null, AC_1));

	}

	public List<ActivityDefinition> getMandatoryActivityNames() {
		return mList;
	}

	public List<ActivityDefinition> getCustomActivityNames(String action,
			String provider) throws ActivityControllException {
		return cList;
	}

	@Override
	public List<ActivityDefinition> getMandatoryActivityNames(String flowName) {
		return mList;
	}

	@Override
	public List<ActivityDefinition> getCustomActivityNames(String flowName,
			String action, String provider) throws ActivityControllException {
		return cList;
	}

	@Override
	public void addFlow(String flowName, List<ActivityDefinition> list)
			throws ActivityControllException {
		throw new RuntimeException("not implemeted");
	}
}
