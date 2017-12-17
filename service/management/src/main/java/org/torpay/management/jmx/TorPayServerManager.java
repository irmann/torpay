package org.torpay.management.jmx;

public interface TorPayServerManager {
	public String getServerName();

	public boolean isServerRunning();

	public void setServerRunning(boolean serverRunning);

	public void flushConfigurations(String instance);
}
