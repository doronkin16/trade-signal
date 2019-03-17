/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.signals;

import java.util.Set;

public interface IExchangeConfig {

    double getChangesProp(String ticker);
    int getChangesByTime();
    Set<String> getTickers();
}
