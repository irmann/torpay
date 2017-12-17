package org.torpay.engine.metadata;

import java.util.List;

public class MetadataParameter {
	private String name;
	private Boolean required;
	private Boolean isArray;
	private String type;
	private String length;
	private String minLength;
	private String maxLength;
	private String mapTo;
	private List<String> custom;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public List<String> getCustom() {
		return custom;
	}

	public void setCustom(List<String> custom) {
		this.custom = custom;
	}

	public Boolean getIsArray() {
		return isArray;
	}

	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMinLength() {
		return minLength;
	}

	public void setMinLength(String minLength) {
		this.minLength = minLength;
	}

	public String getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}
}
