package org.torpay.common.log;

import org.slf4j.Logger;
import org.torpay.common.request.ActionRequest;

public class LogUtil {
	
	static public void logTrace(Logger logger, String log,
			ActionRequest actionRequest) {
		if (logger.isTraceEnabled())
			logger.trace(log);
		actionRequest.appendLog(log);
	}

	static public void logDebug(Logger logger, String log,
			ActionRequest actionRequest) {
		if (logger.isDebugEnabled())
			logger.debug(log);
		actionRequest.appendLog(log);

	}

}
