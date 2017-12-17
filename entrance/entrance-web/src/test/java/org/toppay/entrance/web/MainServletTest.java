package org.toppay.entrance.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

//@ContextConfiguration(locations = { "classpath:entrance-web-test.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
public class MainServletTest {
	protected static final String CONTEXT = "PA1";
	protected static final String MERCHANT = "MERCHANT1";
	protected static final String PROVIDER = "TP";
	protected static final String BASE_URL = "https://test.torpay.com/"
			+ CONTEXT + "/DM1/";
	protected static final String URL = BASE_URL + MERCHANT + "/" + PROVIDER
			+ "/";
	protected static final String URL_INIT = BASE_URL + MERCHANT + "/"
			+ PROVIDER + "/init";
	protected static final String initContent = "{\"version\":\"1.0\",\"provider\":\"$p\",\"amount\":\"4.99\",\"card_num\" :\"411111111111111\",\"exp_date\" : \"0515\"}";

	ServletRunner sr = new ServletRunner();

	@Before
	public void setup() {
		sr.registerServlet("", MainServlet.class.getName());
	}

	@Test
	public void testDoPostExcetiopn() throws IOException, SAXException {
		MainServlet.testUnKnwonExcetion = true;
		ServletUnitClient sc = sr.newClient();
		WebRequest request = new PostMethodWebRequest(
				"http://test.example.com/context", new ByteArrayInputStream(
						"test text".getBytes()), "application/json");
		request.setParameter("testParameterName", "testParameterValue");
		WebResponse response = sc.getResponse(request);
		Assert.assertTrue(response.getText().indexOf(
				MainServlet.UNKNOWN_ERROR_FOR_REQUEST) > -1);
	}

	@Test
	public void testDoGetExcetiopn() throws IOException, SAXException {
		MainServlet.testUnKnwonExcetion = true;
		ServletUnitClient sc = sr.newClient();
		WebRequest request = new GetMethodWebRequest(
				"http://test.example.com/context");
		request.setParameter("testParameterName", "testParameterValue");
		WebResponse response = sc.getResponse(request);
		Assert.assertTrue(response.getText().indexOf(
				MainServlet.UNKNOWN_ERROR_FOR_REQUEST) > -1);
	}
}
