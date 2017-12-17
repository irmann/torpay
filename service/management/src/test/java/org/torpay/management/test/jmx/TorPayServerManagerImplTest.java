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
import org.torpay.management.jmx.TorPayServerManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/management-jmx.xml","classpath:spring/management-jmx-client.xml" })
public class TorPayServerManagerImplTest {

	static final Logger LOG = LoggerFactory
			.getLogger(TorPayServerManagerImplTest.class);

	@Autowired
	private MBeanServerConnection clientConnector = null;

	@Autowired
	@Qualifier("torPayServerManagerProxy")
	private TorPayServerManager torPayServerManager = null;

	@Test
	public void testMBeanServerConnection() {
		MBeanInfo beanInfo = null;

		try {
			Thread.sleep(1000);
			beanInfo = clientConnector.getMBeanInfo(new ObjectName(
					"org.torpay.management.jmx:name=TorPayServerManager"));
			testServerManagerRemoteProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testServerManagerRemoteProxy() {

		LOG.info("serverName={}, serverRunning={}",
				torPayServerManager.getServerName(),
				torPayServerManager.isServerRunning());

		torPayServerManager.flushConfigurations(null);

	}
}
