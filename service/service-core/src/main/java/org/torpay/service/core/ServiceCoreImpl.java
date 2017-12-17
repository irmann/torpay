package org.torpay.service.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.torpay.common.exception.ServiceException;
import org.torpay.common.interfaces.ContentHandler;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Status;
import org.torpay.persistence.service.PersistenceException;
import org.torpay.service.core.activity.controller.ActivityController;
import org.torpay.service.core.activity.controller.ActivityControllException;
import org.torpay.service.core.activity.controller.CoreActivityNames;
import org.torpay.service.proxy.ProxyEngine;
import org.torpay.service.proxy.ProxyPersistence;

@Component
public class ServiceCoreImpl implements ServiceCore {

	static final Logger LOG = LoggerFactory.getLogger(ServiceCoreImpl.class);

	@Autowired
	ActivityController activityController;

	public ServiceCoreImpl() {
	}

	/*
	 * 
	 * steps: *generate transaction id *generate trace number *insert action
	 * request *extract parameter from content *validate general parameters
	 * *validate merchant *check requestId *security check *check IP block
	 * *check merchant block *check fraud *check limit and restriction *find
	 * action(base on internal or external provider) and execute action *update
	 * action request
	 */
	public ActionResponse process(ActionRequest actionRequest) {
		ActionResponse actionResponse = createEmptyActionResponse();
		try {
			init(actionRequest, actionResponse);
			activityController.process(actionRequest, actionResponse);
			actionResponse.setErrorCode(ErrorCodes.SUCCESS.getCode());
		} catch (ActivityControllException abe) {
			handleError(actionRequest, actionResponse, abe);
		} catch (Throwable th) {
			handleError(actionRequest, actionResponse,
					makeServiceExceptionForUnknownError(th));
		} finally {
			close(actionRequest, actionResponse);
		}
		return actionResponse;
	}

	private ActivityControllException makeServiceExceptionForUnknownError(
			Throwable th) {
		return new ActivityControllException(ErrorCodes.TECHNICAL_ERROR,
				"internal error during request process " + th.getMessage(),
				"ServiceCore", th);
	}

	private void init(ActionRequest actionRequest, ActionResponse actionResponse) {
		actionRequest.setFlowName(CoreActivityNames.FLOW_PROVIDER);
	}

	private void close(ActionRequest actionRequest,
			ActionResponse actionResponse) {
		// TODO:actionRequest log to a persist log repository like db
		actionResponse.setEntranceReference(actionRequest.getRequest_db_id());

	}

	private ActionResponse createEmptyActionResponse() {
		ActionResponse actionResponse = new ActionResponse();

		return actionResponse;
	}

	public void handleError(ActionRequest actionRequest,
			ActionResponse actionResponse, ActivityControllException se) {
		try {
			String errorLog = se.getActivityName() + ":code<"
					+ se.getErrorCode().getCode() + ">,message<"
					+ se.getErrorCode().getMessage() + ">";
			LOG.error(errorLog, se);
			actionRequest.appendLog(errorLog);
			actionResponse.setStatus(Status.FAILED.toString());
			actionResponse.setMessageStatus(se.getErrorCode().getMessage());
			actionResponse.setErrorCode(se.getErrorCode().getCode());
			actionResponse.setMessage(se.getMessage());
			actionResponse.setMessageParameters(se.getMessageParameters());
			actionResponse.getDetail().setTechnicalErrorMessage(errorLog);
			actionResponse.getDetail().setRootCauseStackTrace(
					getRootCauseStackTrace(se));
			actionResponse.getDetail().setFullStackTraceMessages(
					getFullStackTraceMessages(se));
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			se.printStackTrace(pw);
			actionResponse.getDetail().setStackTrace(sw.toString());
		} catch (Throwable t) {
			String errorMsg = "error while handleError in ServiceCoreImpl:"
					+ (t != null ? t.getMessage() : "");
			LOG.error(errorMsg, t);
			actionRequest.appendLog(errorMsg);
		}
	}

	private String getFullStackTraceMessages(ActivityControllException se) {
		if (se == null)
			return "";
		Throwable e = (Throwable) se;
		StringBuffer sb = new StringBuffer();
		while (e != null) {
			sb.append(e.getMessage());
			sb.append(",");
			e = e.getCause();
		}
		return sb.toString();
	}

	private String getRootCauseStackTrace(ActivityControllException se) {
		if (se == null)
			return "";
		Throwable e = (Throwable) se;
		while (e.getCause() != null)
			e = e.getCause();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}

	public ContentHandler getContentHandler() throws ServiceException {
		return ProxyEngine.getEngineContent();
	}

	public void storeRequestLog(String request, String response, Long reference)
			throws ServiceException {
		try {
			ProxyPersistence.storeRequestLog(request, response, reference);
		} catch (PersistenceException pe) {
			new ServiceException(pe.getErrorCode(), pe.getMessage(),
					pe.getActivityName(), pe);
		}
	}
}
