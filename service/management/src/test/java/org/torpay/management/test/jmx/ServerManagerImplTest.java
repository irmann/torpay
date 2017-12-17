package org.torpay.management.test.jmx;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/management-test.xml" })
public class ServerManagerImplTest {

	static final Logger LOG = LoggerFactory
			.getLogger(ServerManagerImplTest.class);

	@Autowired
	private MBeanServerConnection clientConnector = null;

	@Autowired
	@Qualifier("serverManagerProxy")
	private ServerManager serverManager = null;

	@Test
	public void testMBeanServerConnection() {
		MBeanInfo beanInfo = null;

		try {
			Thread.sleep(10000);
			beanInfo = clientConnector.getMBeanInfo(new ObjectName(
					"org.torpay.management.test.jmx:name=ServerManager"));
			testServerManagerRemoteProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testServerManagerRemoteProxy() {

		LOG.info("serverName={}, serverRunning={}",
				serverManager.getServerName(), serverManager.isServerRunning());

		int min = 10;
		int max = 20;

		int result = serverManager.changeConnectionPoolSize(min, max);

	}
}
