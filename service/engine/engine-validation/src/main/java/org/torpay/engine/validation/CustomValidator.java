package org.torpay.engine.validation;

import org.torpay.common.util.Parameter;

public interface CustomValidator {

	void validate(Parameter parameter) throws ValidationException;

}
