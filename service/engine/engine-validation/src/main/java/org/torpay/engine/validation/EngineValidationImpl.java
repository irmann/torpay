package org.torpay.engine.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.common.util.ErrorCode;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;
import org.torpay.engine.content.EngineContent;
import org.torpay.engine.metadata.EngineMetaData;
import org.torpay.engine.metadata.MetadataException;
import org.torpay.engine.metadata.MetadataParameter;

public class EngineValidationImpl implements EngineValidation {

	protected static EngineMetaData engineMetaData;
	protected static EngineContent engineContent;
	static final Logger LOG = LoggerFactory
			.getLogger(EngineValidationImpl.class);

	static {
		if (engineMetaData == null) {
			LOG.info("loading appContext from engine-validation.xml ... ");
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/engine-validation.xml" });
			BeanFactory factory = (BeanFactory) appContext;
			engineMetaData = (EngineMetaData) factory.getBean("engineMetaData");
			engineContent = (EngineContent) factory.getBean("engineContent");
		}
	}

	public void validate(String provider, String action, List<Parameter> values)
			throws ValidationException {
		List<MetadataParameter> metadataParameters = new ArrayList<MetadataParameter>();
		try {
			metadataParameters = engineMetaData.extractParametersMetadata(
					provider, action);
		} catch (MetadataException me) {
			throwTechnicalException("error during getting metadata parameters",
					me);
		}
		for (MetadataParameter metadataParameter : metadataParameters) {
			LOG.debug("validating " + metadataParameter.getName());
			validateParameter(metadataParameter, values);
		}
	}

	private static void validateParameter(MetadataParameter metadataParameter,
			List<Parameter> values) throws ValidationException {
		if (metadataParameter.getIsArray() != null
				&& metadataParameter.getIsArray()) {
			List<Parameter> parameters = getValues(values,
					metadataParameter.getName());
			for (Parameter parameter : parameters)
				validateAllCases(metadataParameter, parameter);
		} else {
			Parameter parameter = getValue(values, metadataParameter.getName());
			validateAllCases(metadataParameter, parameter);
		}

	}

	private static List<Parameter> getValues(List<Parameter> values, String name)
			throws ValidationException {
		if (name == null && name.isEmpty())
			throwTechnicalException("name is empty for a metadataParameter",
					null);
		return engineContent.findParameters(values, name);
	}

	private static void validateAllCases(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		validateMandatory(metadataParameter, parameter);
		validateValue(metadataParameter, parameter);
		validateType(metadataParameter, parameter);
		validateLength(metadataParameter, parameter);
		validateCustom(metadataParameter, parameter);
	}

	private static void validateValue(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		String[] errorMessageParameters = new String[] { metadataParameter
				.getName() };
		if (parameter != null) {
			if (parameter.getValue() == null) {
				throwValidationException(
						ErrorCodes.SERVICE_VALIDATION_REQUIRED_PARAMETER_VALUE_MISSED,
						"$1 has empty value in request", errorMessageParameters);
			}
		} else
			LOG.warn("parameter is null in for validateValue");

	}

	private static void validateCustom(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		if (metadataParameter.getCustom() != null
				&& metadataParameter.getCustom().size() > 0) {
			if (parameter != null) {
				if (parameter.getValue() != null) {
					for (String customvalidatorName : metadataParameter
							.getCustom()) {
						CustomValidator customValidator = CustomValidatorfactory
								.getValidator(customvalidatorName);
						if (customValidator != null)
							customValidator.validate(parameter);
						else
							LOG.error("customValidator is null for metadataParameter="
									+ metadataParameter.getName()
									+ " ,customValidatorId="
									+ customvalidatorName);
					}
				} else
					LOG.warn("value of parameter" + parameter.getName()
							+ " is null in for validateCustom");
			} else
				LOG.warn("parameter is null in for validateCustom");
		} else
			LOG.debug("metadataParameter has no custom validation:"
					+ metadataParameter.getName());

	}

	private static Parameter getValue(List<Parameter> values, String name)
			throws ValidationException {
		if (name == null && name.isEmpty())
			throwTechnicalException("name is empty for a metadataParameter",
					null);
		return engineContent.findParameter(values, name);
	}

	private static void validateType(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		String[] errorMessageParameters = new String[] { metadataParameter
				.getName() };
		if (parameter != null) {
			if (parameter.getValue() != null) {
				if (metadataParameter.getType() != null) {
					if (metadataParameter.getType().equals("number")) {

						if (!NumberUtils.isNumber(parameter.getValue())) {
							throwValidationException(
									ErrorCodes.SERVICE_VALIDATION_TYPE_NUMBER_EXPECTED,
									"type of $1 is wrong. number type expected",
									errorMessageParameters);
						}
					} else if (metadataParameter.getType().equals("float")) {
						if (!NumberUtils.isNumber(parameter.getValue())
								&& !(parameter.getValue().indexOf(".") > -1)) {
							throwValidationException(
									ErrorCodes.SERVICE_VALIDATION_TYPE_FLOAT_EXPECTED,
									"type of $1 is wrong. float type expected",
									errorMessageParameters);
						}
					}
				} else
					LOG.warn("type is null for metadataParameter:"
							+ metadataParameter.getName());
			} else
				LOG.warn("value of parameter" + parameter.getName()
						+ " is null in for validateType");
		} else
			LOG.warn("parameter is null in for validateType");
	}

	private static void validateLength(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		String[] errorMessageParameters = new String[] { metadataParameter
				.getName() };
		if (parameter != null) {
			if (parameter.getValue() != null) {
				if (metadataParameter.getLength() != null) {
					if (NumberUtils.isNumber(metadataParameter.getLength())) {
						if (parameter.getValue().length() != NumberUtils
								.toInt(metadataParameter.getLength())) {
							throwValidationException(
									ErrorCodes.SERVICE_VALIDATION_LENGTH,
									"length of $1 is wrong. expected length="
											+ metadataParameter.getLength()
											+ ", real length="
											+ parameter.getValue().length(),
									errorMessageParameters);
						}
					} else
						LOG.warn("length is not number for metadataParameter="
								+ metadataParameter.getName() + " ,lenght="
								+ metadataParameter.getLength());
				} else
					LOG.warn("length is null for metadataParameter:"
							+ metadataParameter.getName());
				if (metadataParameter.getMaxLength() != null) {
					if (NumberUtils.isNumber(metadataParameter.getMaxLength())) {
						if (parameter.getValue().length() > NumberUtils
								.toInt(metadataParameter.getMaxLength())) {
							throwValidationException(
									ErrorCodes.SERVICE_VALIDATION_LENGTH_MAX,
									"length of $1 is wrong. max expected length="
											+ metadataParameter.getMaxLength()
											+ ", real length="
											+ parameter.getValue().length(),
									errorMessageParameters);
						}
					} else
						LOG.warn("max length is not number for metadataParameter="
								+ metadataParameter.getName()
								+ " ,max lenght="
								+ metadataParameter.getMaxLength());
				} else
					LOG.warn("max length is null for metadataParameter:"
							+ metadataParameter.getName());
				if (metadataParameter.getMinLength() != null) {
					if (NumberUtils.isNumber(metadataParameter.getMinLength())) {
						if (parameter.getValue().length() < NumberUtils
								.toInt(metadataParameter.getMinLength())) {
							throwValidationException(
									ErrorCodes.SERVICE_VALIDATION_LENGTH_MIN,
									"length of $1 is wrong. min expected length="
											+ metadataParameter.getMinLength()
											+ ", real length="
											+ parameter.getValue().length(),
									errorMessageParameters);
						}
					} else
						LOG.warn("min length is not number for metadataParameter="
								+ metadataParameter.getName()
								+ " ,min lenght="
								+ metadataParameter.getMinLength());
				} else
					LOG.warn("min length is null for metadataParameter:"
							+ metadataParameter.getName());
			} else
				LOG.warn("value of parameter" + parameter.getName()
						+ " is null in for validateLength");
		} else
			LOG.warn("parameter is null in for validateLength");
	}

	private static void validateMandatory(MetadataParameter metadataParameter,
			Parameter parameter) throws ValidationException {
		if (metadataParameter.getRequired() != null
				&& metadataParameter.getRequired()) {
			String[] errorMessageParameters = new String[] { metadataParameter
					.getName() };
			if (parameter == null) {
				throwValidationException(
						ErrorCodes.SERVICE_VALIDATION_REQUIRED_PARAMETER_MISSED,
						"$1 is missed in request", errorMessageParameters);
			}
		} else
			LOG.debug("parameter is not required:"
					+ metadataParameter.getName());

	}

	private static void throwValidationException(ErrorCode errorCode,
			String message, String[] errorMessageParameters)
			throws ValidationException {
		throw new ValidationException(errorCode, message, "validationService",
				errorMessageParameters);

	}

	private static void throwTechnicalException(String message, Exception e)
			throws ValidationException {
		if (e != null)
			throw new ValidationException(ErrorCodes.TECHNICAL_ERROR, message,
					"validationService", e);
		else
			throw new ValidationException(ErrorCodes.TECHNICAL_ERROR, message,
					"validationService");
	}

}
