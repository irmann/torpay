package org.torpay.tool.cli;

import net.dharwin.common.tools.cli.api.CLIContext;
import net.dharwin.common.tools.cli.api.CommandLineApplication;
import net.dharwin.common.tools.cli.api.annotations.CLIEntry;
import net.dharwin.common.tools.cli.api.exceptions.CLIInitException;

@CLIEntry
public class TorPayCLIClient extends CommandLineApplication<TorPayCLIContext> {
	public TorPayCLIClient() throws CLIInitException {
		super();
	}

	@Override
	protected void shutdown() {
		System.out.println("Shutting down TorPayCLIClient.");
	}

	@Override
	protected CLIContext createContext() {
		return new TorPayCLIContext(this);
	}

}
