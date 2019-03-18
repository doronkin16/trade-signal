/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.spring;

import com.tradesignal.configuration.*;

public class CExchangeConfigFactory {

    public CAbstractExchangeConfig getExchangeConfig(EExchange exchange) {
        if (exchange != null) {
            switch (exchange) {
                case BINANCE:
                    return CSpringContextUtils.getBean(CBinanceConfig.class);
            }
        }
        return null;
    }
}
