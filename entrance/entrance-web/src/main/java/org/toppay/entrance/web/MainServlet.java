package org.toppay.entrance.web;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.common.util.ComponentNames;
import org.torpay.entarnce.core.EntranceCore;
import org.torpay.util.spring.RemoteBeanFactoryUtil;

public class MainServlet extends HttpServlet {
	static final String UNKNOWN_ERROR_FOR_REQUEST = "{ \"message\":\"unknown error. please call support\"}";
	// TODO take this from enranceCore and by a type
	// TODO remove all <spring.version>3.2.3.RELEASE</spring.version>
	// and <junit.version>4.11</junit.version> from pom.xml files

	static final Logger LOG = LoggerFactory.getLogger(MainServlet.class);

	private static final long serialVersionUID = 1L;

	static Boolean testUnKnwonExcetion = false;

	@Autowired
	private EntranceCore entranceCore;
	// TODO all bean are looked up by component-scan feature of spring

	private static String instance;

	private static ClassPathXmlApplicationContext appContext;

	static {
		try {
			if (appContext == null) {
				LOG.info("loading appContext from entrance-web.xml ... ");
				appContext = new ClassPathXmlApplicationContext(
						new String[] { "spring/entrance-web.xml" });
				// TODO all spring context xml file in all modules in parent
				// pom.xml
				// should be moved to a spring folder name
			}
		} catch (Exception e) {
			LOG.error("error in MainServlet during init of MainServlet ", e);
		}
	}

	public MainServlet() throws Exception {
		super();
		try {
			entranceCore = getEntranceCore();
		} catch (Exception e) {
			LOG.error("error in MainServlet during init of MainServlet ", e);
		}
	}

	private EntranceCore getEntranceCore() throws Exception {
		// TODO all spring beans in all modules in parent pom.xml
		// should be used in this way to be looked up
		return (EntranceCore) RemoteBeanFactoryUtil.getPppropriateBeanFactory(
				instance, (BeanFactory) appContext,
				ComponentNames.COMPONENT_ENRANCE_WEB,
				ComponentNames.COMPONENT_ENRANCE_CORE,
				ComponentNames.BEAN_ENRANCE_CORE);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String requestContent = null;
		String responseContent = null;
		try {
			if (testUnKnwonExcetion) {
				testUnKnwonExcetion = false;
				throw new Exception("test");
			}
			requestContent = IOUtils.toString(request.getInputStream());
			if (LOG.isTraceEnabled())
				LOG.trace(requestToString(request, requestContent));
			responseContent = entranceCore.processWebRequest(request, response,
					requestContent);
		} catch (Throwable t) {
			LOG.error(
					"error in MainServlet:"
							+ requestToString(request, responseContent), t);
			IOUtils.write(MainServlet.UNKNOWN_ERROR_FOR_REQUEST.getBytes(),
					response.getOutputStream());
		} finally {
			if (LOG.isTraceEnabled())
				LOG.trace("http response:"
						+ responsetToString(response, responseContent));
		}
	}

	private String responsetToString(HttpServletResponse response,
			String responseContent) {
		StringBuffer ret = new StringBuffer();
		ret.append("http response:");
		ret.append(responseContent);
		return ret.toString();
	}

	private void testException() throws Exception {
		if (testUnKnwonExcetion) {
			LOG.info("testUnKnwonExcetion=" + testUnKnwonExcetion);
			testUnKnwonExcetion = false;
			throw new Exception("testExection");
		}
	}

	private String requestToString(HttpServletRequest request,
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

	public static String getInstance() {
		return instance;
	}

	public static void setInstance(String instance) {
		MainServlet.instance = instance;
	}
}
