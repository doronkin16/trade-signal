/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.spring;

import com.tradesignal.configuration.*;
import com.tradesignal.exchanges.api.*;
import com.tradesignal.signals.*;
import org.springframework.beans.*;
import org.springframework.context.*;

public class CSpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static CAppConfig getAppConfig() {
        return applicationContext.getBean(CAppConfig.class);
    }

    public static CAbstractExchangeConfig getExchangeConfig(EExchange exchange) {
        if (exchange != null) {
            switch (exchange) {
                case BINANCE:
                    return getApplicationContext().getBean(CBinanceConfig.class);
            }
        }
        return null;
    }

    public static CPriceSignals createSignal(EExchange exchange) {
        if (exchange != null) {
            switch (exchange) {
                case BINANCE:
                    return new CPriceSignals(getApplicationContext().getBean(CBinanceService.class),
                            getApplicationContext().getBean(CBinanceConfig.class));
            }
        }
        return null;
    }
}
