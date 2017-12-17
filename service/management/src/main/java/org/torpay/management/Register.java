package org.torpay.management;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Register {

	public static void registerJMX() {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
				new String[] { "spring/management-jmx.xml" });

	}

}
