package org.torpay.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MetaDataUtil {
	private static final String PROVIDER = "provider";
	private static final String ACTIONS = "actions";
	private static final String CONTENT_PARAMETERS = "content-parameters";

	static public String getProviderId(String id, String metadata)
			throws Exception {
		JSONObject json = getRoot(metadata);
		return JSONUtil.extractObject(json, PROVIDER).toString();
	}

	private static JSONObject getRoot(String metadata) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject json = (JSONObject) parser.parse(metadata);
		return json;
	}

	static public String getAction(String providerId, String metadata)
			throws Exception {
		// JSONObject json = getRoot(metadata);
		//
		// Iterator<Entry> iterator = json.entrySet().iterator();
		// while (iterator.hasNext()) {
		// Entry entry =(Entry)iterator.next();
		// entry.getKey().toString().equals(anObject)
		// System.out.println();
		// }

		// JSONArray actions = (JSONArray)JSONUtil.extractObject(json, ACTIONS);
		// JSONArray actions = (JSONArray) json.get(ACTIONS);
		// Iterator<String> iterator = actions.iterator();
		// while (iterator.hasNext()) {
		// System.out.println(iterator.next());
		// }
		return null;
	}

	public static List<Parameter> getContentParameters(String actionId,
			String metadata) throws ParseException {

		List<Parameter> result = new ArrayList<Parameter>();
		JSONObject json = getRoot(metadata);
		JSONArray actions = (JSONArray) json.get(ACTIONS);
		Iterator<JSONObject> iterator = actions.iterator();
		while (iterator.hasNext()) {
			JSONObject action = (JSONObject)  iterator.next();
			String name = (String)action.get("name");
			if(name.equals(actionId)){
				JSONArray parameters = (JSONArray) action.get(CONTENT_PARAMETERS);
				Iterator<JSONObject> parametersIterator =parameters.iterator();
				while (parametersIterator.hasNext()) {
					JSONObject parameter = (JSONObject)  parametersIterator.next();
					Parameter p =new Parameter();
					p.setName((String)parameter.get("name"));
					p.setType((String)parameter.get("type"));
					result.add(p);
				}
			}
		}
		return result;
	}
}
