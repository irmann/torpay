package org.torpay.entrance.convertor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Parameter;

public class JSONConvertor {
	static final Logger LOG = LoggerFactory.getLogger(JSONConvertor.class);

	public static List<Parameter> getValues(String content)
			throws EntranceException {

		JSONParser parser = new JSONParser();
		JSONObject json = null;
		List<Parameter> result = new ArrayList<Parameter>();
		try {
			json = (JSONObject) parser.parse(content);
		} catch (Exception e) {
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_JSON_CONTENT,
					"can not parse the json content:<" + content + ">",
					"JSONConvertor", e);
		}
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
						} else
							LOG.warn("value of " + name
									+ " in json content is not string");
						result.add(p);
					}
				}
			}
		} catch (Exception e) {
			throw new EntranceException(ErrorCodes.TECHNICAL_ERROR,
					"error during parsing json content", "JSONConvertor", e);
		}
		return result;
	}

}