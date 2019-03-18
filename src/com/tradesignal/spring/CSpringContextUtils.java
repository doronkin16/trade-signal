/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.spring;

import com.tradesignal.configuration.*;
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

    public static <T> T getBean(Class<T> t) {
        return getApplicationContext().getBean(t);
    }

    public static CAppConfig getAppConfig() {
        return applicationContext.getBean(CAppConfig.class);
    }

    public static CPriceSignals getPriceSignals() {
        return applicationContext.getBean(CPriceSignals.class);
    }

    public static IExchangeConfig getExchangeConfig() {
        return applicationContext.getBean(CExchangeConfigFactory.class).getExchangeConfig(getAppConfig().getExchange());
    }

    public static CAbstractExchangeConfig getAbstractExchangeConfig(EExchange exchange) {
        return applicationContext.getBean(CExchangeConfigFactory.class).getExchangeConfig(exchange);
    }
}
