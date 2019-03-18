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
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Scope;

public class CContextConfiguration {

    @Bean(initMethod="initProperties")
    public CAppConfig appConfig() {
        return new CAppConfig();
    }

    @Bean(initMethod="initProperties")
    @Autowired
    public CBinanceConfig binanceConfig(CBinanceService binanceService) {
        return new CBinanceConfig(binanceService.loadAllTickers());
    }

    @Bean
    public CBinanceService binanceService() {
        return new CBinanceService();
    }
    @Bean
    public CExchangeServiceFactoryBean exchangeService() {
        return new CExchangeServiceFactoryBean();
    }

    @Bean
    public CExchangeConfigFactory exchangeConfigFactory() {
        return new CExchangeConfigFactory();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public CPriceSignals priceSignals(IExchangeService exchangeService, IExchangeConfig config) {
        return new CPriceSignals(exchangeService, config);
    }
}
