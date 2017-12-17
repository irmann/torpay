package org.torpay.management;

import org.torpay.common.util.FlushUtil;

public class FlushConfigurations {

	public static void flush(String instance) {
		FlushUtil.CONFIGURATION_FLUSH = true;
		FlushUtil.METEDATA_FLUSH = true;
		FlushUtil.UTIL_SPRING_FLUSH = true;
	}

}
