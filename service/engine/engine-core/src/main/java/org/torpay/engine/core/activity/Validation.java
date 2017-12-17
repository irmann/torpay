package org.torpay.engine.core.activity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.torpay.common.request.ProviderRequest;
import org.torpay.common.util.LogLevel;
import org.torpay.engine.metadata.EngineMetaData;
import org.torpay.engine.util.ActivityNames;
import org.torpay.engine.validation.EngineValidation;
import org.torpay.engine.workflow.Activity;
import org.torpay.engine.workflow.WorkflowContext;
import org.torpay.engine.workflow.WorkflowException;

public class Validation extends BaseActivity implements Activity {

	private static final Log LOG = LogFactory.getLog(Validation.class);

	@Autowired
	private EngineMetaData engineMetaData;

	@Autowired
	private EngineValidation engineValidation;

	public String getName() {
		return ActivityNames.VALIDATION;
	}

	@Override
	protected void activityExecute(WorkflowContext context)
			throws WorkflowException {
		log("enter", LogLevel.TRACE, LOG);
		ProviderRequest providerRequest = getProviderRequest(context);
		if (providerRequest == null)
			throwTechnicalException("providerRequest is null");
		if (providerRequest.getProvider() == null)
			throwTechnicalException("providerRequest.provider is null");
		if (providerRequest.getAction() == null)
			throwTechnicalException("providerRequest.action is null");
		try {
			if (engineMetaData.getMetaData(providerRequest.getProvider()) == null)
				throwTechnicalException("provider is invalid. there is not a metadata for "
						+ providerRequest.getProvider());
			engineValidation.validate(providerRequest.getProvider(),
					providerRequest.getAction(), providerRequest.getValues());
		} catch (Exception e) {
			throwGeneralTechnicalError(e);
		}
		log("exit", LogLevel.TRACE, LOG);
	}

}
