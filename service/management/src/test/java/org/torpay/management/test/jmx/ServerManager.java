package org.torpay.management.test.jmx;

public interface ServerManager {
	public String getServerName();

	public boolean isServerRunning();

	public void setServerRunning(boolean serverRunning);

	public int changeConnectionPoolSize(int minPoolSize, int maxPoolSize);
}
