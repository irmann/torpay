package org.torpay.common.util;

import java.util.Formatter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONUtil {

	public static Object extractObject(Object json, Object... args) {
		Object currentLocation = json;
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof String) {
				currentLocation = ((JSONObject) currentLocation).get(args[i]);
			} else if (args[i] instanceof Integer) {
				currentLocation = ((JSONArray) currentLocation)
						.get((Integer) args[i]);
			} else {
				throw new IllegalArgumentException(format(
						"Argument %d is neither a String nor an Integer, %s",
						i, args[i]));
			}
		}
		return currentLocation;
	}

	public static String format(String format, Object... args) {
		Formatter fmt = new Formatter();
		fmt.format(format, args);
		String res = fmt.toString();
		fmt.close();
		return res;
	}
}
