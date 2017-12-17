package org.torpay.entarnce.core;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class EntranceCoreUtil {
	public static String requestToString(HttpServletRequest request,
			String requestContent) {
		StringBuffer ret = new StringBuffer();
		ret.append("http request:");
		ret.append("AuthType=");
		ret.append(request.getAuthType());
		ret.append(",");
		ret.append("CharacterEncoding=");
		ret.append(request.getCharacterEncoding());
		ret.append(",");
		ret.append("ContentType=");
		ret.append(request.getContentType());
		ret.append(",");
		ret.append("ContextPath=");
		ret.append(request.getContextPath());
		ret.append(",");
		ret.append("Method=");
		ret.append(request.getMethod());
		ret.append(",");
		ret.append("PathInfo=");
		ret.append(request.getPathInfo());
		ret.append(",");
		ret.append("PathTranslated=");
		ret.append(request.getPathTranslated());
		ret.append(",");
		ret.append("Protocol=");
		ret.append(request.getProtocol());
		ret.append(",");
		ret.append("QueryString=");
		ret.append(request.getQueryString());
		ret.append(",");
		ret.append("RemoteAddr=");
		ret.append(request.getRemoteAddr());
		ret.append(",");
		ret.append("RemoteHost=");
		ret.append(request.getRemoteHost());
		ret.append(",");
		ret.append("RemoteUser=");
		ret.append(request.getRemoteUser());
		ret.append(",");
		ret.append("RequestedSessionId=");
		ret.append(request.getRequestedSessionId());
		ret.append(",");
		ret.append("RequestURI=");
		ret.append(request.getRequestURI());
		ret.append(",");
		ret.append("RequestURL=");
		ret.append(request.getRequestURL());
		ret.append(",");
		ret.append("ServerName=");
		ret.append(request.getServerName());
		ret.append(",");
		ret.append("ServerPort=");
		ret.append(request.getServerPort());
		ret.append(",");
		ret.append("ServletPath=");
		ret.append(request.getServletPath());
		ret.append(",");
		ret.append("Body=");
		ret.append(requestContent);
		ret.append(",");
		ret.append("ParameterNames=");
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
}
