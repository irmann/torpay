package org.torpay.tool.cli;

import net.dharwin.common.tools.cli.api.Command;
import net.dharwin.common.tools.cli.api.CommandResult;
import net.dharwin.common.tools.cli.api.annotations.CLICommand;
import net.dharwin.common.tools.cli.api.console.Console;

import org.torpay.management.ClientManager;
import org.torpay.security.SecurityCheck;

import com.beust.jcommander.Parameter;

@CLICommand(name = "flush", description = "Flush to configurations be refreshed.")
public class FlushConfigurationsCommand extends Command<TorPayCLIContext> {

	@Parameter(names = { "-p", "--password" }, description = "Passorwd for command.", required = true)
	private String _password;

	@Parameter(names = { "-i", "--instance" }, description = "Instance of configurations.", required = false)
	private String _instance;

	@Override
	public CommandResult innerExecute(TorPayCLIContext context) {
		try {
			Console.info("Flush is done.");
			if (SecurityCheck.isValidPassword(_password)) {
				try {
					ClientManager.registerClientJMX();
				} catch (Exception e) {
					Console.error(e.getMessage());
					Console.error("can not connect to server.");
					return CommandResult.ERROR;
				}
				FlushConfigurationsService.flush(_instance);
			} else
				Console.error("password is wrong");
		} catch (Exception t) {
			Console.error("error during executing command:" + t.getMessage());
			t.printStackTrace();
			return CommandResult.ERROR;
		}
		return CommandResult.OK;
	}

}
