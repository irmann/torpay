package org.torpay.common.util;

import java.util.List;

public class Parameter {

	private String name;
	private String type;
	private String value;
	private List[] arrayValue;
	private List<Parameter> objectValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {

		return "<Parameter: name=" + name + " ,value=" + value + " ,type="
				+ type + " arrayValue=" + toString(arrayValue)
				+ " objectValue=" + toString(objectValue) + ">";
	}

	public static String toString(List<Parameter> objectValue2) {
		StringBuffer sb = new StringBuffer();
		if (objectValue2 != null) {
			for (Parameter p : objectValue2)
				sb.append(" " + p.toString());
		}

		return sb.toString();
	}

	private String toString(List<Parameter>[] arrayValue2) {
		StringBuffer sb = new StringBuffer();
		if (arrayValue2 != null) {
			for (List<Parameter> list : arrayValue2)
				for (Parameter p : list)
					sb.append(" " + p.toString());
		}

		return sb.toString();
	}

	public List[] getArrayValue() {
		return arrayValue;
	}

	public void setArrayValue(List[] arrayValue) {
		this.arrayValue = arrayValue;
	}

	public List<Parameter> getObjectValue() {
		return objectValue;
	}

	public void setObjectValue(List<Parameter> objectValue) {
		this.objectValue = objectValue;
	}
}
