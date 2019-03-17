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
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

public class CContextConfiguration {

    @Bean(initMethod="initProperties")
    public CBinanceConfig binanceConfig() {
        return new CBinanceConfig(binanceService().loadAllTickers());
    }

    @Bean(initMethod="initProperties")
    public CAppConfig appConfig() {
        return new CAppConfig();
    }

    @Bean
    public CBinanceService binanceService() {
        return new CBinanceService();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @DependsOn(value = {"binanceService", "binanceConfig"})
    public CPriceSignals binancePriceSignal() {
        return new CPriceSignals(binanceService(), binanceConfig());
    }
}
