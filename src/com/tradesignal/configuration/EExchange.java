/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.configuration;

public enum EExchange {
    BINANCE("Binance", "binance.cfg");

    private String name;
    private String configFile;

    EExchange(String name, String configFile) {
        this.name = name;
        this.configFile = configFile;
    }

    public String getName() {
        return name;
    }

    public String getConfigFile() {
        return configFile;
    }
}
