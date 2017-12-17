package org.toppay.entrance.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorizeNetTest extends MainServletTest {

	private static final String AUTHORIZE_NET = "authorizeNet";
	ServletRunner sr = new ServletRunner();
	String authorizeNetAIMContent = initContent;

	@Before
	public void setup() {
		initContent.replace("$p", AUTHORIZE_NET);
		sr.registerServlet("", MainServlet.class.getName());
	}

	@Test
	public void authorizeNetAIMSuccessTest()  {
		try{
		ServletUnitClient sc = sr.newClient();
		WebRequest request = new PostMethodWebRequest(URL_INIT,
				new ByteArrayInputStream(initContent.getBytes()),
				"application/json");
		WebResponse response = sc.getResponse(request);
		}catch (Exception e) {
			e.printStackTrace();
		}
		// assertNotNull( "No response received", response );
		// assertEquals( "content type", "text/plain", response.getContentType()
		// );
	}
}
