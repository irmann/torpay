package org.torpay.management.test.jmx;

import javax.management.MBeanInfo;
import javax.management.ObjectName;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.management.ClientManager;
import org.torpay.management.Register;

public class TorPayServerManagerImplTest2 {

	static final Logger LOG = LoggerFactory
			.getLogger(TorPayServerManagerImplTest2.class);

	@Test
	public void testMBeanServerConnection() {
		MBeanInfo beanInfo = null;
		Register.registerJMX();
		try {
			Thread.sleep(3000);
			ClientManager.registerClientJMX();
			beanInfo = ClientManager.clientConnector
					.getMBeanInfo(new ObjectName(
							"org.torpay.management.jmx:name=TorPayServerManager"));
			testServerManagerRemoteProxy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testServerManagerRemoteProxy() {

		LOG.info("serverName={}, serverRunning={}",
				ClientManager.torPayServerManager.getServerName(),
				ClientManager.torPayServerManager.isServerRunning());

		ClientManager.torPayServerManager.flushConfigurations(null);

	}
}
