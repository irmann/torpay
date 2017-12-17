package org.torpay.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class NotificationProxy {

	@Autowired
	public static Notification notification;
	private static boolean flag = false;
	static {
		if (!flag) {
			flag = true;
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "spring/notification.xml" });

		}
	}
}
