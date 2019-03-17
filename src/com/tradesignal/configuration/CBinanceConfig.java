/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.configuration;

import java.util.Set;

public class CBinanceConfig extends CAbstractExchangeConfig {

    public CBinanceConfig(Set<String> tickers) {
        super(tickers);
    }

    @Override
    public EExchange getExchangeType() {
        return EExchange.BINANCE;
    }
}
