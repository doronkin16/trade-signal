/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.signals;

import java.math.BigDecimal;
import java.util.*;
import java.util.Timer;

public class CPriceSignals {

    private IExchangeService exchangeService;
    private IExchangeConfig exchangeConfig;

    private List<IPriceLoadListener> priceListeners = new ArrayList<>();
    private List<IErrorListener> errorListeners = new ArrayList<>();
    private Timer timer;

    public CPriceSignals(IExchangeService exchangeService, IExchangeConfig exchangeConfig) {
        this.exchangeService = exchangeService;
        this.exchangeConfig = exchangeConfig;
    }

    public void startLoadedPriceLoop() {
        timer = new java.util.Timer(true);
        timer.scheduleAtFixedRate(new PriceUpdater(), 2000, 5000);
    }


    public void stopLoadedPriceLoop() {
        timer.cancel();
    }

    public void addPriceLoopListener(IPriceLoadListener listener) {
        priceListeners.add(listener);
    }

    public void addErrorListener(IErrorListener listener) {
        errorListeners.add(listener);
    }

    private void firePriceListeners(Map<String, BigDecimal> tickerPersant, Map<String, BigDecimal> signalPrices ) {
        priceListeners.forEach(listener -> listener.pricesLoaded(tickerPersant, signalPrices));
    }

    private void fireErrorListeners(Exception e) {
        errorListeners.forEach(listener -> listener.errorHandler(e));
    }

    class PriceUpdater extends TimerTask {
        Queue<Map<String, BigDecimal>> queue = new LinkedList();

        @Override
        public void run() {
            try {
                Map<String, BigDecimal> currentPrices = exchangeService.loadLastPrice(exchangeConfig.getTickers());

                Map<String, BigDecimal> prevPrices = null;
                int quqiSize = exchangeConfig.getChangesByTime() / 5 + 1;
                if (queue.size() < quqiSize) {
                    prevPrices = queue.peek();
                } else if (queue.size() >= quqiSize) {
                    for (int i = 0; i <= queue.size() - quqiSize; i++) {
                        prevPrices = queue.poll();
                    }
                }

                queue.add(currentPrices);
                if (prevPrices == null) {
                    return;
                }

                Map<String, BigDecimal> percent = new HashMap<>();
                Map<String, BigDecimal> signalPrices = new HashMap<>();
                final Map<String, BigDecimal> prevPricesFinal = prevPrices;
                currentPrices.forEach((ticker, currentPrice) -> {
                    BigDecimal prevPrice = prevPricesFinal.getOrDefault(ticker, BigDecimal.ZERO);
                    BigDecimal percentChanges = currentPrice.multiply(BigDecimal.valueOf(100)).divide(prevPrice, 4, 0).subtract(BigDecimal.valueOf(100));
                    percent.put(ticker, percentChanges);

                    if (percentChanges.doubleValue() >= exchangeConfig.getChangesProp(ticker)) {
                        signalPrices.put(ticker, percentChanges);
                    }
                });

                firePriceListeners(percent, signalPrices);

            } catch (Exception e) {
                fireErrorListeners(e);
            }
        }
    }
}
