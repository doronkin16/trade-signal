/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.signals;

import java.math.BigDecimal;
import java.util.Map;

public interface IPriceLoadListener {
    void pricesLoaded(Map<String, BigDecimal> allTickerPrices, Map<String, BigDecimal> signalPrices);
}
