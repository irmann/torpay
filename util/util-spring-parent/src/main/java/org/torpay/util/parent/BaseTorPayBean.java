package org.torpay.util.parent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.torpay.management.ManagerInit;
import org.torpay.notification.NotificationConstants;
import org.torpay.notification.NotificationProxy;

public class BaseTorPayBean {
	static final Logger LOG = LoggerFactory.getLogger(BaseTorPayBean.class);
	private static boolean flag = false;
	static {
		generalInit();
	}

	synchronized static protected void generalInit() {
		try {
			if (!flag) {
				ManagerInit.Init();
			}
		} catch (Throwable t) {
			NotificationProxy.notification.notify(NotificationConstants.GENERAL_INIT_ERROR,"error during general init. "+t.getMessage());
			LOG.error("error during init a bean", t);
		} finally {
			flag = true;

		}
	};
}
