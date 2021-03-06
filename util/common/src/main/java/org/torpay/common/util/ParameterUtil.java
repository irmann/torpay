package org.torpay.common.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterUtil {

	static final Logger LOG = LoggerFactory.getLogger(ParameterUtil.class);

	static public List<Parameter> findParameters(List<Parameter> parameters,
			String name) {
		try {
			if (name != null) {

				String currentPart = setCurrentPart(name);
				String restPart = setRestPart(name);
				LOG.debug("currentPart=" + currentPart + ", restPart="
						+ restPart);

				List<Parameter> result = new ArrayList<Parameter>();
				for (Parameter parameter : parameters) {
					if (parameter.getName() != null
							&& parameter.getName().equals(currentPart)) {
						LOG.debug("parameter: " + parameter);
						if (restPart == null && parameter.getValue() != null) {
							result.add(parameter);
						} else

						if (restPart != null && restPart.indexOf(".") == -1
								&& parameter.getArrayValue() != null) {
							loadParametersFromArrayValues(restPart, result,
									parameter);

						} else if (restPart == null
								&& parameter.getArrayValue() != null)
							loadParametersFromArrayValues(restPart, result,
									parameter);
						else if (parameter.getObjectValue() != null
								&& restPart == null) {
							for (Parameter parameter2 : parameter
									.getObjectValue())
								result.add(parameter2);
						} else if (parameter.getObjectValue() != null)
							return findParameters(parameter.getObjectValue(),
									restPart);
						else if (parameter.getArrayValue() != null) {
							List<Parameter> innerResult = new ArrayList<Parameter>();
							// restPart = setRestPart(restPart);
							for (List<Parameter> l : parameter.getArrayValue()) {
								List<Parameter> tmpResult = findParameters(l,
										restPart);
								if (tmpResult != null)
									innerResult.addAll(tmpResult);
							}
							result.addAll(innerResult);
						}

						return result;

					}
				}
			} else
				LOG.debug("name is null");
		} catch (Throwable th) {
			LOG.error(
					"error during parsing content. Parameter:<"
							+ Parameter.toString(parameters) + " name=" + name
							+ ">", th);
		}
		return null;
	}

	static private String setCurrentPart(String name) {

		if (name.indexOf(".") > -1) {
			return name.substring(0, name.indexOf("."));
			// restPart = name.substring(name.indexOf(".") + 1, name.length());
		}
		return name;
	}

	static private String setRestPart(String name) {

		if (name.indexOf(".") > -1) {
			// name.substring(0, name.indexOf("."));
			return name.substring(name.indexOf(".") + 1, name.length());
		}
		return null;
	}

	static private List<Parameter> loadParametersFromArrayValues(
			String restPart, List<Parameter> result, Parameter parameter) {
		if (parameter.getArrayValue() != null)
			for (List<Parameter> lp : parameter.getArrayValue())
				for (Parameter p : lp)
					if (restPart == null)
						result.add(p);
					else if (p.getName() != null
							&& p.getName().equals(restPart)) {
						if (p.getArrayValue() != null) {
							loadParametersFromArrayValues(null, result, p);
						} else
							result.add(p);
					}
		return result;
	}

	static public Parameter findParameter(List<Parameter> list, int count) {
		int index = 1;
		for (Parameter p : list)
			if (index == count)
				return p;
			else
				index++;
		return null;
	}
	
	static public Parameter findParameter(List<Parameter> parameters, String name) {

		List<Parameter> ret = findParameters(parameters, name);
		if (ret != null)
			return findParameter(ret, 1);
		return null;
	}
}
