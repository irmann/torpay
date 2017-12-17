package org.torpay.entarnce.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.torpay.common.exception.EntranceException;
import org.torpay.common.interfaces.ContentHandler;
import org.torpay.common.test.ContentHandlerTestImpl;
import org.xml.sax.SAXException;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:entrance-core.xml" })
public class EntranceCoreImplTest {

    ContentHandler contentHandler = new ContentHandlerTestImpl();
	
	private static final String PAYMENT = "payment";
	
	@Autowired
	EntranceCore entranceCore;

	@Before
	public void setup() {

	}

	@Test
	public void test() throws IOException, SAXException, EntranceException {
	}

	@Test
	public void testDoPostHttpToActionRequestSuccessful() throws Exception {
//		HttpServletRequest req = mock(HttpServletRequest.class);
//		HttpServletResponse res = mock(HttpServletResponse.class);
//		String content = "{\"name1\":\"value1\", \"name2\":\"value2\"}";
//		when(req.getParameter("firstName")).thenReturn("johannes");
//		when(req.getContentType()).thenReturn("application/json");
//		when(req.getRequestURI()).thenReturn(
//				"/payment/merchant/privider/action?test=1");
//		StringBuffer sb = new StringBuffer(
//				"http://test.torpay.com/payment/merchant/privider/action?test=1");
//		when(req.getRequestURL()).thenReturn(sb);
//		when(req.getInputStream()).thenReturn(
//				createServletInputStream(content, "UTF-8"));
//		entranceCore.processWebRequest(req, res, content);
        
	}

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
