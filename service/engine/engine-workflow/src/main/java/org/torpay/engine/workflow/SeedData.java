package org.torpay.engine.workflow;

import java.util.HashMap;

public class SeedData {

	private HashMap<String, Object> data = new HashMap<String, Object>();

	public SeedData() {

	}

	public HashMap<String, Object> getData() {
		return data;
	}

	public Object getValue(String key) {
		return data.get(key);
	}

	public void setValue(String key, Object value) {
		this.data.put(key, value);
	}

}
