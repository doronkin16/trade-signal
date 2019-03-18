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
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;

public class CExchangeServiceFactoryBean implements FactoryBean<IExchangeService> {

    @Autowired
    private CAppConfig appConfig;

    @Override
    public IExchangeService getObject() {
        switch (appConfig.getExchange()) {
            case BINANCE:
                return CSpringContextUtils.getBean(CBinanceService.class);
        }
        return null;
    }

    @Override
    public Class<IExchangeService> getObjectType() {
        return IExchangeService.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
