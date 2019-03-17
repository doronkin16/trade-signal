/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.exchanges.api;

import java.math.BigDecimal;
import java.util.*;

import com.tradesignal.exchanges.api.exceptions.CExchangeException;
import org.json.*;

public class CBinanceService extends CAbstractExchangeService {
    private static final String BASE_POINT = "https://api.binance.com/";

//    private static final String QUERY_PING = "/api/v1/ping";
    private static final String QUERY_BOOK_TICKER = "/api/v1/exchangeInfo";
    private static final String QUERY_LAST_PRICE = "api/v3/ticker/price";

    public CBinanceService() {
    }

    @Override
    protected String request(String url) {
        String result = super.request(url);
        if (result.charAt(0) == '{') {
            JSONObject obj = new JSONObject(result);
            if (obj.has("code") && obj.has("msg")) {
                throw new CExchangeException("Request error! Code = " + obj.getString("code") + " message ='" + obj.getString("msg") + "'");
            }
        }
        return result;
    }

    @Override
    public Set<String> loadAllTickers() throws CExchangeException {
        String res = request(BASE_POINT + QUERY_BOOK_TICKER);

        JSONObject obj = new JSONObject(res);

        JSONArray symbolsJSON = obj.getJSONArray("symbols");

        Set<String> tickers = new HashSet<>();
        for (int i = 0; i < symbolsJSON.length(); i++) {
            String symbol = symbolsJSON.getJSONObject(i).getString("symbol");
            String status = symbolsJSON.getJSONObject(i).getString("status");
            if (symbol != null && symbol.endsWith("BTC") && Objects.equals(status, "TRADING")) {
                tickers.add(symbol);
            }
        }
        return tickers;
    }


    public Map<String, BigDecimal> loadLastPrice(Set<String> tickers) throws CExchangeException {
        String res = request(BASE_POINT + QUERY_LAST_PRICE);

        JSONArray prices = new JSONArray(res);

        Map<String, BigDecimal> lastPrices = new HashMap<>(tickers.size());

        for (int i = 0; i < prices.length(); i++) {
            JSONObject symbPrice = prices.getJSONObject(i);
            String symbol = symbPrice.getString("symbol");
            if (tickers.contains(symbol)) {
                lastPrices.put(symbol, symbPrice.getBigDecimal("price"));
            }
        }

        return lastPrices;
    }
}
