package org.torpay.engine.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.torpay.common.request.ProviderRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.engine.util.Constants;
import org.torpay.engine.workflow.SeedData;
import org.torpay.engine.workflow.WorkflowProcessor;
import org.torpay.engine.workflow.WorkflowResult;

@Component("engineCore")
public class EngineCoreImpl implements EngineCore{

	@Autowired
	WorkflowProcessor workflowProcessor;

	public void process(ProviderRequest request) throws EngineCoreException {

		try {
			WorkflowResult workflowResult = workflowProcessor
					.doActivities(createSeedData(request));
			if (workflowResult.getWorkflowException() != null)
				throw new EngineCoreException(ErrorCodes.TECHNICAL_ERROR, null,
						"EngineCore", workflowResult.getWorkflowException());
		} catch (Throwable th) {
			throw new EngineCoreException(ErrorCodes.TECHNICAL_ERROR,
					"unknowen error in EngineCore", "EngineCore", th);
		}

	}

	private SeedData createSeedData(ProviderRequest request) {
		SeedData seedData = new SeedData();
		seedData.setValue(Constants.ENGINE_CORE_REQUEST, request);
		return seedData;
	}

}
