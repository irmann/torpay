package org.torpay.engine.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;

public class JSONExtractor implements Extractor {
	static final Logger LOG = LoggerFactory.getLogger(JSONExtractor.class);

	public List<Parameter> extractContent(String content)
			throws GlobalExcetion {

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		List<Parameter> result = new ArrayList<Parameter>();
		try {
			json = (JSONObject) parser.parse(content);
		} catch (Exception e) {
			throw new GlobalExcetion(
					ErrorCodes.SERVICE_CONTENT_INVALID_JSON_CONTENT,
					"can not parse the json content:<" + content + ">",
					"ServiceContent", e);
		}
		result = loadParameters(json);
		return result;
	}

	private List<Parameter> loadParameters(JSONObject json)
			throws GlobalExcetion {
		List<Parameter> result = new ArrayList<Parameter>();
		try {
			if (!json.entrySet().isEmpty()) {
				Iterator<JSONObject> iterator = json.entrySet().iterator();
				while (iterator.hasNext()) {
					Object objectParameter = (Object) iterator.next();
					if (objectParameter instanceof Entry) {
						Entry entryParameter = (Entry) objectParameter;
						String name = (String) entryParameter.getKey();
						Parameter p = new Parameter();
						p.setName(name);

						Object value = entryParameter.getValue();
						if (value instanceof String) {
							p.setValue((String) value);
						} else {
							if (value instanceof JSONObject) {
								p.setObjectValue(loadParameters((JSONObject) value));
							} else if (value instanceof JSONArray) {
								List<List<Parameter>> retArray = new ArrayList<List<Parameter>>();
								Iterator<JSONObject> it = ((JSONArray) value)
										.iterator();
								while (it.hasNext())
									retArray.add((loadParameters((JSONObject) it
											.next())));
								p.setArrayValue(retArray.toArray(new List[retArray.size()]));
							} else
								LOG.debug("value of "
										+ name
										+ " in json content is not string or JSONObject");
						}
						result.add(p);
					}
				}
			}
		} catch (Exception e) {
			throw new GlobalExcetion(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "ServiceContent", e);
		}
		return result;
	}

}
