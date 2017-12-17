package org.torpay.management;

import javax.management.MBeanServerConnection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.torpay.management.jmx.TorPayServerManager;

public class ClientManager {
	private static ClassPathXmlApplicationContext appContext;

	@Autowired
	public static MBeanServerConnection clientConnector = null;

	@Autowired
	@Qualifier("torPayServerManagerProxy")
	public static TorPayServerManager torPayServerManager = null;
	
	public static void registerClientJMX() {
		appContext = new ClassPathXmlApplicationContext(
				new String[] { "spring/management-jmx-client.xml" });
		BeanFactory factory = (BeanFactory) appContext;
		clientConnector=(MBeanServerConnection) factory.getBean("clientConnector");
		torPayServerManager=(TorPayServerManager) factory.getBean("torPayServerManagerProxy");
		

	}

	

}
