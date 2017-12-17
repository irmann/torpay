package org.torpay.tool.cli;

import java.io.File;

import net.dharwin.common.tools.cli.api.CLIContext;

public class TorPayCLIContext extends CLIContext {
    
    public TorPayCLIContext(TorPayCLIClient app) {
            super(app);
   }
   
    @Override
    protected String getEmbeddedPropertiesFilename() {
            return "/embedded_torpay_client.properties";
    }
    
    @Override
    protected File getExternalPropertiesFile() {
            return new File("torpay_client.properties");
    }
}