package org.torpay.entrance.convertor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.request.ActionRequest;
import org.torpay.common.test.ContentHandlerTestImpl;
import org.torpay.common.util.ErrorCodes;
import org.torpay.common.interfaces.ContentHandler;
import org.torpay.entrance.convertor.EntranceConvertor;
import org.xml.sax.SAXException;

@ContextConfiguration(locations = { "classpath:spring/entrance-convertor.xml",
		"classpath:spring/entrance-convertor-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class EntranceConvertorTest {

	ContentHandler contentHandler = new ContentHandlerTestImpl();

	private static final String PAYMENT = "payment";
	@Autowired
	private EntranceConvertor entranceConvertor;

	@Before
	public void setup() {
	}

	@Test
	public void testDoPostExcetionToHttp() {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse res = mock(HttpServletResponse.class);
		when(req.getParameter("firstName")).thenReturn("johannes");
		when(req.getContentType()).thenReturn(null);
		try {

			entranceConvertor.httpToActionRequest(req, PAYMENT, contentHandler,
					null);
		} catch (EntranceException e) {
			entranceConvertor.excetionToHttp(e, res);

		}

	}

	@Test
	public void testDoPostHttpToActionRequestInvalidContentType()
			throws IOException, SAXException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("firstName")).thenReturn("johannes");
		when(req.getContentType()).thenReturn("aplication/invalid");
		try {
			entranceConvertor.httpToActionRequest(req, PAYMENT, contentHandler,
					null);
		} catch (EntranceException e) {
			Assert.assertEquals(
					e.getErrorCode().getCode(),
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_CONTENT_TYPE
							.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void testDoPostHttpToActionRequestInvalidServiceNameInUrl()
			throws IOException, SAXException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getRequestURI()).thenReturn("/invalidServiceName/merchant1");
		when(req.getContentType()).thenReturn("application/json");
		try {
			entranceConvertor.httpToActionRequest(req, PAYMENT, contentHandler,
					null);
		} catch (EntranceException e) {
			Assert.assertEquals(
					e.getErrorCode().getCode(),
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_SERVICE_NAME_IN_URL
							.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void testDoPostHttpToActionRequestInvalidUri() throws IOException,
			SAXException {
		testDoPostHttpToActionRequestInvalidUri("/payment");
		testDoPostHttpToActionRequestInvalidUri("/payment/");
		testDoPostHttpToActionRequestInvalidUri("/payment/merchant");
		testDoPostHttpToActionRequestInvalidUri("/payment/merchant/");
		testDoPostHttpToActionRequestInvalidUri("/payment/merchant/privider");
		testDoPostHttpToActionRequestInvalidUri("/payment/merchant/privider/");
	}

	public void testDoPostHttpToActionRequestInvalidUri(String uri)
			throws IOException, SAXException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getRequestURI()).thenReturn(uri);
		when(req.getContentType()).thenReturn("application/json");
		try {
			entranceConvertor.httpToActionRequest(req, PAYMENT, contentHandler,
					null);
		} catch (EntranceException e) {
			Assert.assertEquals(
					e.getErrorCode().getCode(),
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_URL_FORMAT
							.getCode());
			return;
		}
		Assert.fail();
	}

	@Test
	public void testDoPostHttpToActionRequestNullHttpMethod() throws Exception {
		ServletInputStream servletInputStream = mock(ServletInputStream.class);
		HttpServletRequest req = mock(HttpServletRequest.class);
		String content = "{\"name1\":\"value1\", \"name2\":\"value2\"}";
		when(req.getParameter("firstName")).thenReturn("johannes");
		when(req.getContentType()).thenReturn("application/json");
		when(req.getRequestURI()).thenReturn(
				"/payment/merchant/privider/action?test=1");
		StringBuffer sb = new StringBuffer(
				"http://test.torpay.com/payment/merchant/privider/action?test=1");
		when(req.getRequestURL()).thenReturn(sb);
		try {
			ActionRequest actionRequest = entranceConvertor
					.httpToActionRequest(req, PAYMENT, contentHandler, content);
		} catch (EntranceException e) {
			Assert.assertEquals(e.getErrorCode().getCode(),
					ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_NULL_METHOD
							.getCode());
			return;
		}
		Assert.fail();

	}

	@Test
	public void testDoPostHttpToActionRequestSuccessful() throws Exception {
		ServletInputStream servletInputStream = mock(ServletInputStream.class);
		// servletInputStream = (InputStream) ByteArrayInputStream("");
		HttpServletRequest req = mock(HttpServletRequest.class);
		String content = "{\"name1\":\"value1\", \"name2\":\"value2\"}";
		when(req.getParameter("firstName")).thenReturn("johannes");
		when(req.getContentType()).thenReturn("application/json");
		when(req.getMethod()).thenReturn("POST");
		when(req.getRequestURI()).thenReturn(
				"/payment/merchant/privider/action?test=1");
		StringBuffer sb = new StringBuffer(
				"http://test.torpay.com/payment/merchant/privider/action?test=1");
		when(req.getRequestURL()).thenReturn(sb);
		when(req.getInputStream()).thenReturn(
				createServletInputStream(content, "UTF-8"));
		ActionRequest actionRequest = entranceConvertor.httpToActionRequest(
				req, PAYMENT, contentHandler, content);
		Assert.assertNotNull(actionRequest.getMerchant());
		Assert.assertNotNull(actionRequest.getProvider());
		Assert.assertNotNull(actionRequest.getAction());
		Assert.assertNotNull(actionRequest.getParameters());
		Assert.assertNotNull(actionRequest.getRequest_content());
		Assert.assertNotNull(actionRequest.getRequest_string());
		Assert.assertNotNull(actionRequest.getRequest_parameters());
		// Assert.assertNotNull(actionRequest.getValues());

	}

	// @Test
	// public void testDoPostHttpToActionRequestInvalidJson() throws Exception {
	// try {
	// ServletInputStream servletInputStream = mock(ServletInputStream.class);
	// // servletInputStream = (InputStream) ByteArrayInputStream("");
	// HttpServletRequest req = mock(HttpServletRequest.class);
	// String content = "{\"name1\":, \"name2\":\"value2\"}";
	// when(req.getParameter("firstName")).thenReturn("johannes");
	// when(req.getContentType()).thenReturn("application/json");
	// when(req.getRequestURI()).thenReturn(
	// "/payment/merchant/privider/action?test=1");
	// StringBuffer sb = new StringBuffer(
	// "http://test.torpay.com/payment/merchant/privider/action?test=1");
	// when(req.getRequestURL()).thenReturn(sb);
	// when(req.getInputStream()).thenReturn(
	// createServletInputStream(content, "UTF-8"));
	// ActionRequest actionRequest = entranceConvertor
	// .httpToActionRequest(req, PAYMENT, contentHandler);
	// } catch (EntranceException e) {
	// Assert.assertEquals(
	// e.getErrorCode().getCode(),
	// ErrorCodes.ENTRANCE_CONVERTOR_HTTP_REQUEST_INVALID_JSON_CONTENT
	// .getCode());
	// return;
	// }
	// Assert.fail();
	//
	// }

	public static ServletInputStream createServletInputStream(String s,
			String charset) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(s.getBytes(charset));
		} catch (Exception e) {
			throw new RuntimeException("No support charset.");
		}

		final InputStream bais = new ByteArrayInputStream(baos.toByteArray());

		return new ServletInputStream() {

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}
}
