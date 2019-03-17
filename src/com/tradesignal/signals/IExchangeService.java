/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.signals;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

public interface IExchangeService {

    Set<String> loadAllTickers();

    Map<String, BigDecimal> loadLastPrice(Set<String> tickers);
}
