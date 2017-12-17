package org.toppay.entrance.web;

import java.io.ByteArrayInputStream;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;
import org.torpay.common.util.Constants;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TorPayAuthorizeNetTest extends MainServletTest {

	private static final String AUTHORIZE_NET = "authorizeNet";
	ServletRunner sr = new ServletRunner();
	String authorizeNetAIMContent = initContent;

	protected static final String MERCHANT = "MERCHANT1";
	protected static final String PROVIDER = "torpay-test-2";
	protected static final String BASE_URL = "https://test.torpay.com";
	protected static final String URL = BASE_URL + "/" + Constants.SERVICE_NAME
			+ "/" + MERCHANT + "/" + PROVIDER;
	protected static final String AUTHORIZE_NET_AIM_URL = URL
			+ "/AuthorizeNetAIM";
	protected static final String AuthorizeNetAIMContent = "{\"amount\":\"4.99\",\"card_num\" :\"411111111111111\",\"exp_date\" : \"0515\",\"custom1\" : \"value1\"}";

	@Before
	public void setup() {
		sr.registerServlet("", MainServlet.class.getName());
	}

	@Test
	public void authorizeNetAIMSuccessTest() {
		try {
			StopWatch watch = new StopWatch();
	        watch.start();
			ServletUnitClient sc = sr.newClient();
			WebRequest request = new PostMethodWebRequest(
					AUTHORIZE_NET_AIM_URL, new ByteArrayInputStream(
							AuthorizeNetAIMContent.getBytes()),
					"application/json");
			WebResponse response = sc.getResponse(request);
			System.err
					.println("response text start ----------------------------------------------");
			System.err.println(response.getText());
			System.err
					.println("response text end   ----------------------------------------------");
			watch.stop();
	        System.out.println("Total execution time in millis: "
	                + watch.getTotalTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void authorizeNetAIMSuccessPermormanceTest() {
		try {
			StopWatch watch = new StopWatch();
			ServletUnitClient sc = sr.newClient();
			WebRequest request = new PostMethodWebRequest(
					AUTHORIZE_NET_AIM_URL, new ByteArrayInputStream(
							AuthorizeNetAIMContent.getBytes()),
					"application/json");
			WebResponse response = sc.getResponse(request);
			WebRequest request2 = new PostMethodWebRequest(
					AUTHORIZE_NET_AIM_URL, new ByteArrayInputStream(
							AuthorizeNetAIMContent.getBytes()),
					"application/json");
			watch.start();
			sc.getResponse(request2);
			watch.stop();
	        System.out.println("Total execution time in millis: "
	                + watch.getTotalTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
