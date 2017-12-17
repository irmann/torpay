package org.torpay.service.core.activity.validation;

import org.apache.commons.lang.math.NumberUtils;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;
import org.torpay.engine.validation.CustomValidator;
import org.torpay.engine.validation.ValidationException;

public class TestCustomValidator implements CustomValidator {

	public void validate(Parameter parameter) throws ValidationException {
		String[] errorMessageParameters = new String[] { parameter.getName() };
		if (!NumberUtils.isNumber(parameter.getValue())) {
			throw new ValidationException(
					ErrorCodes.SERVICE_VALIDATION_TYPE_NUMBER_EXPECTED,
					"type of $1 is wrong. number type expected",
					"validationService", errorMessageParameters);
		}

	}

}
