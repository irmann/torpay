package org.torpay.entrance.convertor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.exception.GlobalExcetion;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.request.ActionResponse;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.util.Status;
import org.torpay.configuration.ConfigurationException;
import org.torpay.configuration.ConfigurationKeys;
import org.torpay.configuration.ConfigurationService;
import org.torpay.common.interfaces.ContentHandler;
import org.torpay.common.util.Parameter;

@Component
public class EntranceConvertorImpl implements EntranceConvertor {

	static final Logger LOG = LoggerFactory
			.getLogger(EntranceConvertorImpl.class);
	@Autowired
	private ConfigurationService configurationService;

	protected String activityName = "EntranceConvertor.httpToActionRequest";
	String[] supportedContentType = new String[] { "application/json" };

	public ActionRequest httpToActionRequest(HttpServletRequest request,
			String serviceName, ContentHandler contentHandler, String content)
			throws EntranceException {
		ActionRequest actionRequest = new ActionRequest();
		String contentType = request.getContentType();

		if (serviceName == null || serviceName.isEmpty()) {

			throw new EntranceException(ErrorCodes.TECHNICAL_ERROR,
					"internal error:serviceName is null<" + serviceName + ">",
					activityName);
		}
		if (contentType == null || contentType.isEmpty()) {
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_MISSED_CONTENT_TYPE,
					"content type is not valid. supported content types:"
							+ supportedContenttypes(supportedContentType),
					activityName);
		}
		if (!isSupportedContentType(contentType, supportedContentType)) {
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_CONTENT_TYPE,
					"supported content types:"
							+ supportedContenttypes(supportedContentType),
					activityName);
		}
		String uri = request.getRequestURI();
		if (!uri.startsWith("/" + serviceName))
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_SERVICE_NAME_IN_URL,
					"expected service name is " + serviceName
							+ ", exampel: https://<HOST>/" + serviceName
							+ "/...", activityName);
		if (!validateUri(uri)) {
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_URL_FORMAT,
					"expected format:https://<HOST>/{SERVICE_NAME}/{MERCHNAT_ID}/{PROVIDER_ID}/{ACTION}",
					activityName);
		}
		actionRequest.setMerchant(getPartOfUri(uri, 2));
		actionRequest.setProvider(getPartOfUri(uri, 3));
		actionRequest.setAction(getPartOfUri(uri, 4));
		actionRequest.setParameters(getParameters(request));
		actionRequest.setRequest_content(content);
		actionRequest.setRequest_string(request.getRequestURL().toString());
		actionRequest.setRequest_parameters(parametersToString(request));
		if (request.getMethod() != null) {
			if (request.getMethod().toUpperCase().equals("POST")) {
				if (content == null || content.isEmpty()) {
					throw new EntranceException(
							ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_NULL_CONTENT,
							"for a POST HTTP request contant body of the HTTP request can not be empty",
							activityName);
				} else {
					actionRequest.setValues(getContentValues(content,
							contentType, contentHandler));
				}

			}
		} else {
			throw new EntranceException(
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_NULL_METHOD,
					"for a HTTP request, method should be set:<POST,GET,PUT,DELETE>",
					activityName);
		}

		// extract: action, provider, merchant, parameters, requestContent,
		// requestString,
		// requestParameters , security(password,token,security-code), date
		// payment_number, request_id
		LOG.trace(actionRequest.toString());
		actionRequest.appendLog(actionRequest.toString());
		return actionRequest;
	}

	private List<Parameter> getContentValues(String content,
			String contentType, ContentHandler contentHandler)
			throws EntranceException {
		try {
			return contentHandler.extractContent(content, contentType);
		} catch (GlobalExcetion e) {
			throw new EntranceException(e.getErrorCode(), e.getMessage(),
					e.getActivityName(), e);
		}
	}

	private String parametersToString(HttpServletRequest request) {
		StringBuffer ret = new StringBuffer();
		try {
			Map<String, String[]> map = request.getParameterMap();
			if (map != null && map.size() > 0) {
				for (Entry<String, String[]> entry : map.entrySet()) {
					ret.append("<");
					ret.append(entry.getKey());
					ret.append(",");
					ret.append(entry.getValue()[0]);
					ret.append(">");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}

	private String readContent(HttpServletRequest request) {
		try {
			return IOUtils.toString(request.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HashMap<String, String> getParameters(HttpServletRequest request) {
		HashMap<String, String> ret = new HashMap<String, String>();
		Map<String, String[]> map = request.getParameterMap();
		if (map != null && map.size() > 0) {
			for (Entry<String, String[]> entry : map.entrySet()) {
				ret.put(entry.getKey(), entry.getValue()[0]);
			}
		}
		return ret;
	}

	private Boolean validateUri(String uri) {
		// String path = uri.substring(serviceName+"/".,)
		if (uri.lastIndexOf("?") > -1)
			uri = uri.substring(0, uri.lastIndexOf("?"));
		StringTokenizer uriParts = new StringTokenizer(uri, "/");
		int index = 0;
		// pattern : service/merchant/provide/action
		while (uriParts.hasMoreElements()) {
			String part = (String) uriParts.nextElement();
			if (part != null && !part.isEmpty())
				index++;
		}
		if (index != 4)
			return false;
		else
			return true;

	}

	private String getPartOfUri(String uri, int i) {
		if (uri.lastIndexOf("?") > -1)
			uri = uri.substring(0, uri.lastIndexOf("?"));
		StringTokenizer uriParts = new StringTokenizer(uri, "/");
		int index = 0;
		// pattern : service/merchant/provide/action
		while (uriParts.hasMoreElements()) {
			String part = (String) uriParts.nextElement();
			if (part != null && !part.isEmpty()) {
				index++;
				if (index == i)
					return part;
			}
		}
		return null;
	}

	private boolean isSupportedContentType(String requestContentType,
			String[] supportedContentTypes) {
		for (String contentType : supportedContentTypes) {
			if (contentType.equals(requestContentType))
				return true;
		}
		return false;
	}

	private String supportedContenttypes(String[] supportedContentTypes) {
		StringBuffer supportedContenttypesStr = new StringBuffer();
		for (String contentType : supportedContentTypes) {
			supportedContenttypesStr.append(contentType);
			supportedContenttypesStr.append(",");
		}
		return supportedContenttypesStr.toString();
	}

	public String excetionToHttp(GlobalExcetion excetion,
			HttpServletResponse httpServletResponse) {
		if (excetion == null) {
			LOG.error("excetion is null in excetionToHttp");
			return null;
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		excetion.printStackTrace(pw);
		return writeResponse(httpServletResponse, Status.FAILED.toString(),
				excetion.getErrorCode().getMessage(), excetion.getErrorCode()
						.getCode().toString(), excetion.getMessage(),
				sw.toString(), null, null, null);

	}

	private String writeResponse(HttpServletResponse httpServletResponse,
			String status, String message, String errorCode,
			String technicalErrorMessage, String stackTrace,
			String fullStackTraceMessages, String rootCauseStackTrace,
			String merchantId) {
		JSONObject obj = new JSONObject();
		if (status != null)
			obj.put("status", status);
		if (status != message)
			obj.put("message", message);
		if (status != errorCode)
			obj.put("errorCode", errorCode);
		if (technicalErrorMessage != null || stackTrace != null
				|| fullStackTraceMessages != null
				|| rootCauseStackTrace != null) {

			JSONObject detail = new JSONObject();
			if (technicalErrorMessage != null)
				detail.put("technicalErrorMessage", technicalErrorMessage);
			if (isWriteStackTrack(stackTrace, errorCode, merchantId))
				detail.put("stackTrace", stackTrace);
			if (isFullStackTraceMessages(stackTrace, errorCode, merchantId))
				detail.put("fullStackTraceMessages", fullStackTraceMessages);
			if (isRootCauseStackTrace(stackTrace, errorCode, merchantId))
				detail.put("rootCauseStackTrace", rootCauseStackTrace);

			obj.put("detail", detail);
		}
		StringWriter out = new StringWriter();

		try {
			obj.writeJSONString(out);

			String jsonText = out.toString();
			if (jsonText != null)
				jsonText = jsonText.replace("\\", "");
			LOG.debug("response:" + jsonText);
			if (httpServletResponse.getOutputStream() != null)
				IOUtils.write(jsonText.getBytes(),
						httpServletResponse.getOutputStream());
			return jsonText;
		} catch (IOException e) {
			LOG.error("can not write response", e);
		}
		return null;
	}

	private boolean isFullStackTraceMessages(String stackTrace,
			String errorCode, String merchantId) {
		return isWrite(
				stackTrace,
				errorCode,
				ConfigurationKeys.ENTRANCE_CONVERTOR_FULL_MESSAGE_STACK_TRACE_OUTPUT_ENABLE,
				ConfigurationKeys.ENTRANCE_CONVERTOR_FULL_MESSAGE_STACK_TRACE_OUTPUT_ONLY_UNKNWON_ERROR_ENABLE,
				merchantId);
	}

	private boolean isRootCauseStackTrace(String stackTrace, String errorCode,
			String merchantId) {
		return isWrite(
				stackTrace,
				errorCode,
				ConfigurationKeys.ENTRANCE_CONVERTOR_ROOT_CAUSED_STACK_TRACE_OUTPUT_ENABLE,
				ConfigurationKeys.ENTRANCE_CONVERTOR_ROOT_CAUSED_STACK_TRACE_OUTPUT_ONLY_UNKNWON_ERROR_ENABLE,
				merchantId);
	}

	private boolean isWriteStackTrack(String stackTrace, String errorCode,
			String merchantId) {
		return isWrite(
				stackTrace,
				errorCode,
				ConfigurationKeys.ENTRANCE_CONVERTOR_STACK_TRACE_OUTPUT_ENABLE,
				ConfigurationKeys.ENTRANCE_CONVERTOR_STACK_TRACE_OUTPUT_ONLY_UNKNWON_ERROR_ENABLE,
				merchantId);
	}

	private boolean isWrite(String str, String errorCode, String configName,
			String configNameOnlyUnknownError, String merchantId) {
		try {
			if (str == null)
				return false;
			Boolean active = configurationService.getBoolean(configName, false,
					merchantId);
			if (active == null || !active)
				return false;
			Boolean unknwonErrorActive = configurationService.getBoolean(
					configNameOnlyUnknownError, false, merchantId);
			if (unknwonErrorActive != null
					&& unknwonErrorActive
					& errorCode != null
					&& ErrorCodes.TECHNICAL_ERROR.getCode().intValue() == Integer
							.parseInt(errorCode))
				return true;
		} catch (ConfigurationException e) {
			LOG.error("can not load configurations", e);
		}
		return false;
	}

	public String actionResponseToHttp(ActionResponse actionResponse,
			HttpServletResponse httpServletResponse) throws EntranceException {
		if (actionResponse == null) {
			LOG.error("actionResponse is null in actionResponseToHttp");
			return null;
		}
		return writeResponse(httpServletResponse, actionResponse.getStatus(),
				actionResponse.getFilledMessage(), actionResponse
						.getErrorCode().toString(), actionResponse.getDetail()
						.getTechnicalErrorMessage(), actionResponse.getDetail()
						.getStackTrace(), actionResponse.getDetail()
						.getFullStackTraceMessages(), actionResponse
						.getDetail().getRootCauseStackTrace(),
				actionResponse.getMerchant());

	}
}
