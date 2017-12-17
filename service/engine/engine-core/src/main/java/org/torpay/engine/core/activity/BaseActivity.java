package org.torpay.engine.core.activity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.torpay.common.request.ProviderRequest;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.LogLevel;
import org.torpay.engine.util.Constants;
import org.torpay.engine.workflow.ErrorHandler;
import org.torpay.engine.workflow.WorkflowContext;
import org.torpay.engine.workflow.WorkflowException;

public abstract class BaseActivity {

	protected static final String ERROR_DURING_ACTIVITY = "Error during ";

	@Autowired
	private ErrorHandler errorHandler;

	public abstract String getName();

	private WorkflowContext context;

	private static final Log LOG = LogFactory.getLog(BaseActivity.class);

	public void execute(WorkflowContext context) throws WorkflowException {
		this.context = context;
		try {
			log("enter", LogLevel.TRACE, LOG);
			activityExecute(context);
		} catch (WorkflowException we) {
			throw we;
		} catch (Throwable th) {
			throwGeneralTechnicalError(th);
		} finally {
			log("exit", LogLevel.TRACE, LOG);
		}
	}

	protected abstract void activityExecute(WorkflowContext context)
			throws WorkflowException;

	protected void log(String log, LogLevel level, Log logger, Throwable ex) {
		log = getName() + " > " + log;
		if (LogLevel.TRACE.toString().equals("TRACE")
				&& logger.isTraceEnabled()) {
			if (ex != null)
				logger.trace(log, ex);
			else
				logger.trace(log);
		} else if (LogLevel.INFO.toString().equals("INFO")
				&& logger.isInfoEnabled()) {
			if (ex != null)
				logger.info(log, ex);
			else
				logger.info(log);
		} else if (LogLevel.DEBUG.toString().equals("DEBUG")
				&& logger.isDebugEnabled()) {
			if (ex != null)
				logger.debug(log, ex);
			else
				logger.debug(log);
		} else if (LogLevel.ERROR.toString().equals("ERROR")
				&& logger.isErrorEnabled()) {
			if (ex != null)
				logger.error(log, ex);
			else
				logger.error(log);
		} else if (LogLevel.WARN.toString().equals("WARN")
				&& logger.isWarnEnabled()) {
			if (ex != null)
				logger.warn(log, ex);
			else
				logger.warn(log);
		} else if (LogLevel.FATAL.toString().equals("FATAL")
				&& logger.isFatalEnabled()) {
			if (ex != null)
				logger.fatal(log, ex);
			else
				logger.fatal(log);
		}
		getContext().appendActivityLog(log);
	}

	protected void log(String log, LogLevel level, Log logger) {
		log(log, level, logger, null);

	}

	protected void log(String log, LogLevel level) {
		log(log, level, LOG, null);
	}

	protected void throwGeneralTechnicalError(Throwable th)
			throws WorkflowException {
		throw new WorkflowException(ErrorCodes.TECHNICAL_ERROR,
				ERROR_DURING_ACTIVITY + getName(), getName(), th);
	}

	protected WorkflowContext getContext() {
		return context;
	}

	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	public void throwTechnicalException(String message)
			throws WorkflowException {
		throw new WorkflowException(ErrorCodes.TECHNICAL_ERROR,
				ERROR_DURING_ACTIVITY + getName() + ":" + message, getName(),
				null);
	}

	protected ProviderRequest getProviderRequest(WorkflowContext context) {
		return (ProviderRequest) context
				.getAttribute(Constants.ENGINE_CORE_REQUEST);

	}
}
