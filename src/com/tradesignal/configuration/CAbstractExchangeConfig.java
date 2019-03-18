/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tradesignal.signals.*;

public abstract class CAbstractExchangeConfig extends CAbstractConfigFile implements IExchangeConfig {

    private final static String PROP_CHANGES_TIME = "PROP_CHANGES_TIME";

    private final static int defaultChangesTime = 60; //in seconds
    private final static double defaultChangesPercent = 1d;

    private Set<String> tickers;

    public CAbstractExchangeConfig(Set<String> tickers) {
        this.tickers = tickers;
    }

    public abstract EExchange getExchangeType();

    @Override
    protected String getConfigFileName() {
        return getExchangeType().getConfigFile();
    }

    @Override
    public void initProperties() {
        File propFile = new File(getConfigFileName());

        if (!propFile.exists()) {
            properties.setProperty(PROP_CHANGES_TIME, String.valueOf(defaultChangesTime));
            tickers.forEach(ticker -> properties.setProperty(ticker, String.valueOf(defaultChangesPercent)));
            savePropFile();
        } else {
            loadPropFile();
        }
    }

    public double getChangesProp(String ticker) {
        return Double.parseDouble(properties.getProperty(ticker, String.valueOf(defaultChangesPercent)));
    }

    public void setChangesByTime(int seconds) {
        properties.setProperty(PROP_CHANGES_TIME, String.valueOf(seconds));
    }

    public int getChangesByTime() {
        return Integer.valueOf(properties.getProperty(PROP_CHANGES_TIME, String.valueOf(defaultChangesTime)));
    }

    public void setChangesProp(String ticker, Double value) {
        properties.setProperty(ticker, String.valueOf(value));
    }

    public Map<String, Double> getTickersValues() {
        Map<String, Double> tickersProperties = new HashMap<>();
        tickers.forEach(t -> tickersProperties.put(t, getChangesProp(t)));
        return tickersProperties;
    }

    public Set<String> getTickers() {
        return tickers;
    }
}
