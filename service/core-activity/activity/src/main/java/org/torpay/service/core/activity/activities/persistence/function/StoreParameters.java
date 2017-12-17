package org.torpay.service.core.activity.activities.persistence.function;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.util.Constants;
import org.torpay.common.util.Parameter;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.activities.persistence.LoadActionRequestParametersActivity;

public class StoreParameters {
	static final Logger LOG = LoggerFactory
			.getLogger(LoadActionRequestParametersActivity.class);

	static public void store(ActionRequest actionRequest,
			PersistenceCoreActivityService persistenceCoreActivityService)
			throws PersistenceException {
		store(actionRequest.getValues(), actionRequest.getRequest_db_id(),
				null, null, persistenceCoreActivityService);
	}

	static public void store(List<Parameter> values, Long requestId,
			String parent, String type,
			PersistenceCoreActivityService persistenceCoreActivityService)
			throws PersistenceException {
		for (Parameter parameter : values) {
			if (parameter.getValue() != null) {
				RequestParameter requestParameter = createNotNullValueRequestParameter(
						parameter, requestId, parent, null);
				persistenceCoreActivityService
						.saveRequestParameter(requestParameter);
				LOG.debug("RequestParameter is stored:"
						+ requestParameter.toString());
			} else if (parameter.getObjectValue() != null) {
				RequestParameter requestParameter = createObjectValueRequestParameter(
						parameter, requestId, parent, null);
				persistenceCoreActivityService
						.saveRequestParameter(requestParameter);
				LOG.debug("RequestParameter is stored:"
						+ requestParameter.toString());
				store(parameter.getObjectValue(), requestId,
						parameter.getName(), null,
						persistenceCoreActivityService);
			} else if (parameter.getArrayValue() != null
					&& parameter.getArrayValue().length > 0) {
				RequestParameter requestParameter = createArrayValueRequestParameter(
						parameter, requestId, parent, Constants.TYPE_ARRAY);
				persistenceCoreActivityService
						.saveRequestParameter(requestParameter);
				LOG.debug("RequestParameter is stored:"
						+ requestParameter.toString());
				for (List list : parameter.getArrayValue())
					store(list, requestId, parameter.getName(),
							null,
							persistenceCoreActivityService);
			}

		}

	}

	private static RequestParameter createArrayValueRequestParameter(
			Parameter parameter, Long requestId, String parent, String type) {
		return createRequestParameter(parameter, requestId, parent, type);
	}

	private static RequestParameter createObjectValueRequestParameter(

	Parameter parameter, Long requestId, String parent, String type) {
		return createRequestParameter(parameter, requestId, parent, type);
	}

	private static RequestParameter createNotNullValueRequestParameter(
			Parameter parameter, Long requestId, String parent, String type) {
		return createRequestParameter(parameter, requestId, parent, type);
	}

	private static RequestParameter createRequestParameter(Parameter parameter,
			Long requestId, String parent, String type) {
		RequestParameter requestParameter = new RequestParameter();
		requestParameter.setName(parameter.getName());
		requestParameter.setValue(parameter.getValue());
		requestParameter.setCreationDate(new DateTime(new Date()));
		requestParameter.setCreator("Core");
		requestParameter.setRequestId(requestId);
		requestParameter.setParent(parent);
		if (type != null)
			requestParameter.setType(type);
		else
			requestParameter.setType(parameter.getType());
		return requestParameter;
	}

}
