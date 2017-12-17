package org.torpay.engine.validation;

import java.util.List;

import org.torpay.common.util.Parameter;

public interface EngineValidation {
	public void validate(String provider, String action, List<Parameter> values)
			throws ValidationException;
}
