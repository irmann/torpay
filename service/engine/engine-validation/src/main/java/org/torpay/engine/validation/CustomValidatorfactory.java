package org.torpay.engine.validation;

public class CustomValidatorfactory {

	public static CustomValidator getValidator(String customvalidatorClass) {
		Class c = null;
		try {
			c = Class.forName(customvalidatorClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		CustomValidator customValidator = null;
		try {
			customValidator = (CustomValidator) c.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return customValidator;
	}

}
