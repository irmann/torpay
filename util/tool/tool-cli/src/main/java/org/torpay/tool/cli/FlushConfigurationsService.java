package org.torpay.tool.cli;

import org.torpay.management.ClientManager;

public class FlushConfigurationsService {

	public static void flush(String instance) {
		ClientManager.torPayServerManager.flushConfigurations(instance);
	}
}
