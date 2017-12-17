package org.torpay.service.core.activity.activities.persistence.function;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.util.Constants;
import org.torpay.common.util.Parameter;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.service.PersistenceCoreActivityService;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.activities.persistence.LoadActionRequestParametersActivity;

public class LoadParameters {
	static final Logger LOG = LoggerFactory
			.getLogger(LoadActionRequestParametersActivity.class);

	static public List<Parameter> load(Long request_id,
			PersistenceCoreActivityService persistenceCoreActivityService)
			throws PersistenceException {
		List<RequestParameter> allParameters = persistenceCoreActivityService
				.findRequestParameterByRequestId(request_id);
		return load(allParameters, null, persistenceCoreActivityService);
	}

	static public List<Parameter> load(List<RequestParameter> allParameters,
			String parent,
			PersistenceCoreActivityService persistenceCoreActivityService)
			throws PersistenceException {
		List<Parameter> result = new ArrayList<Parameter>();
		List<RequestParameter> parametersByParent = fetchByParent(
				allParameters, parent);
		for (RequestParameter requestParameter : parametersByParent) {
			if(requestParameter.getName().equals("name4"))
				System.out.println(" stop ");
			if (requestParameter.getValue() != null) {
				Parameter parameter = createParameter(requestParameter);
				result.add(parameter);
				LOG.debug("Parameter is load under root parent:"
						+ parameter.toString());
			} else if (requestParameter.getType() != null
					&& requestParameter.getType().equals(Constants.TYPE_ARRAY)) {
				Parameter parameter = createParameter(requestParameter);
				LOG.debug("Array Parameter is load under " + parent
						+ " parent:" + parameter.toString());
				List<Parameter> arrayParameters = load(allParameters,
						requestParameter.getName(),
						persistenceCoreActivityService);
				List<List<Parameter>> retArray = new ArrayList<List<Parameter>>();
				for(Parameter parameter3:arrayParameters ){
					List<Parameter> innerList = new ArrayList<Parameter>();
					innerList.add(parameter3);
					retArray.add(innerList);
				}
				parameter.setArrayValue(retArray.toArray(new List[retArray.size()]));
				result.add(parameter);
			} else {
				List<RequestParameter> objectparameters = fetchByParent(
						allParameters, requestParameter.getName());
				if (objectparameters != null && objectparameters.size() > 0) {
					Parameter parameter = createParameter(requestParameter);
					LOG.debug("Parameter is load under " + parent + " parent:"
							+ parameter.toString());
					List<Parameter> objectParameters = load(allParameters,
							requestParameter.getName(),
							persistenceCoreActivityService);
					parameter.setObjectValue(objectParameters);
					result.add(parameter);
				}
			}
		}
		return result;
	}

	private static Parameter createParameter(RequestParameter requestParameter) {
		Parameter parameter = new Parameter();
		parameter.setName(requestParameter.getName());
		parameter.setValue(requestParameter.getValue());
		return parameter;
	}

	private static List<RequestParameter> fetchByParent(
			List<RequestParameter> allParameters, String parent) {
		List<RequestParameter> ret = new ArrayList<RequestParameter>();

		if (parent == null) {
			for (RequestParameter requestParameter : allParameters)
				if (requestParameter.getParent() == null)
					ret.add(requestParameter);
		} else if (parent != null) {
			for (RequestParameter requestParameter : allParameters)
				if (requestParameter.getParent() != null
						&& requestParameter.getParent().equals(parent))
					ret.add(requestParameter);
		}
		return ret;
	}
}
