package org.torpay.management.jmx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;
import org.torpay.management.FlushConfigurations;

@Component
@ManagedResource(objectName = "org.torpay.management.jmx:name=TorPayServerManager", description = "TorPay Server Manager.")
public class TorPayServerManagerImpl implements TorPayServerManager {

	static final Logger LOG = LoggerFactory.getLogger(TorPayServerManagerImpl.class);

	private String serverName = "TorPayServerManager";
	private boolean serverRunning = true;

	/**
	 * Gets server name.
	 */
	@ManagedAttribute(description = "The server name.")
	public String getServerName() {
		return serverName;
	}

	/**
	 * Whether or not the server is running.
	 */
	@ManagedAttribute(description = "Server's running status.")
	public boolean isServerRunning() {
		return serverRunning;
	}

	/**
	 * Sets whether or not the server is running.
	 */
	@ManagedAttribute(description = "Whether or not the server is running.", currencyTimeLimit = 20, persistPolicy = "OnUpdate")
	public void setServerRunning(boolean serverRunning) {
		this.serverRunning = serverRunning;
	}

	@ManagedOperation(description = "flush configurations of a instance to be updated")
	@ManagedOperationParameters({
			@ManagedOperationParameter(name = "instance", description = "Instance Name")})
	public void flushConfigurations(String instance) {
         FlushConfigurations.flush(instance);
         LOG.info("flushConfigurations is called");

	}

}
