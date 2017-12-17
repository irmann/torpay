package org.torpay.service.core.activity.controller;

import static org.torpay.service.core.activity.controller.CoreActivityNames.ACTION_HANDLER;
import static org.torpay.service.core.activity.controller.CoreActivityNames.CLIENT_REQUIST_ID_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.FLOW_PROVIDER;
import static org.torpay.service.core.activity.controller.CoreActivityNames.FRAUD_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.GENERAL_PARAMETERS_VALIDATION;
import static org.torpay.service.core.activity.controller.CoreActivityNames.GENERATE_TRACE_NUMBER;
import static org.torpay.service.core.activity.controller.CoreActivityNames.GENERATE_TRANSACTION_CODE;
import static org.torpay.service.core.activity.controller.CoreActivityNames.INIT;
import static org.torpay.service.core.activity.controller.CoreActivityNames.IP_BLOCK_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.LIMIT_AND_RESTRICTION_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.MERCHANT_VALIDATION;
import static org.torpay.service.core.activity.controller.CoreActivityNames.MERCHNT_BLOCK_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.SECURITY_CHECK;
import static org.torpay.service.core.activity.controller.CoreActivityNames.STORE_ACTION_REQUEST;
import static org.torpay.service.core.activity.controller.CoreActivityNames.STORE_ACTION_REQUEST_2;
import static org.torpay.service.core.activity.controller.CoreActivityNames.STORE_ACTION_REQUEST_PARAMETER;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityFlowImpl implements ActivityFlow {

	private static HashMap<String, List<ActivityDefinition>> flows = new HashMap<String, List<ActivityDefinition>>();

	static public List<ActivityDefinition> mList = new ArrayList<ActivityDefinition>();
	static {
		mList.add(new ActivityDefinition(INIT, GENERATE_TRANSACTION_CODE, null));
		mList.add(new ActivityDefinition(GENERATE_TRANSACTION_CODE,
				GENERATE_TRACE_NUMBER, null));
		mList.add(new ActivityDefinition(GENERATE_TRACE_NUMBER,
				STORE_ACTION_REQUEST, null));
		// mList.add(new ActivityDefinition(EXTRACT_CONTENT_VALUES,
		// STORE_ACTION_REQUEST, null));
		mList.add(new ActivityDefinition(STORE_ACTION_REQUEST,
				STORE_ACTION_REQUEST_PARAMETER, null));
		mList.add(new ActivityDefinition(STORE_ACTION_REQUEST_PARAMETER,
				GENERAL_PARAMETERS_VALIDATION, null));
		mList.add(new ActivityDefinition(GENERAL_PARAMETERS_VALIDATION,
				MERCHANT_VALIDATION, null));
		mList.add(new ActivityDefinition(MERCHANT_VALIDATION,
				CLIENT_REQUIST_ID_CHECK, null));
		mList.add(new ActivityDefinition(CLIENT_REQUIST_ID_CHECK,
				SECURITY_CHECK, null));
		mList.add(new ActivityDefinition(SECURITY_CHECK, IP_BLOCK_CHECK, null));
		mList.add(new ActivityDefinition(IP_BLOCK_CHECK, MERCHNT_BLOCK_CHECK,
				null));
		mList.add(new ActivityDefinition(MERCHNT_BLOCK_CHECK, FRAUD_CHECK, null));
		mList.add(new ActivityDefinition(FRAUD_CHECK,
				LIMIT_AND_RESTRICTION_CHECK, null));
		mList.add(new ActivityDefinition(LIMIT_AND_RESTRICTION_CHECK,
				ACTION_HANDLER, null));
		mList.add(new ActivityDefinition(ACTION_HANDLER,
				STORE_ACTION_REQUEST_2, null));
		mList.add(new ActivityDefinition(STORE_ACTION_REQUEST_2, null, null));

		flows.put(FLOW_PROVIDER, mList);
	}

	public List<ActivityDefinition> getMandatoryActivityNames() {
		throw new RuntimeException("not implemeted");
	}

	public List<ActivityDefinition> getCustomActivityNames(String action,
			String provider) {
		throw new RuntimeException("not implemeted");
	}

	@Override
	public List<ActivityDefinition> getMandatoryActivityNames(String flowName) {
		return flows.get(flowName);
	}

	@Override
	public List<ActivityDefinition> getCustomActivityNames(String flowName,
			String action, String provider) throws ActivityControllException {
		return new ArrayList<ActivityDefinition>();
	}

	@Override
	public void addFlow(String flowName, List<ActivityDefinition> list)
			throws ActivityControllException {
		flows.put(flowName, list);

	}
}
