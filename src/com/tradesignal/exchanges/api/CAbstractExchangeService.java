/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.exchanges.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.tradesignal.exchanges.api.exceptions.CExchangeException;
import com.tradesignal.signals.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

public abstract class CAbstractExchangeService implements IExchangeService {
    private HttpClient client = new DefaultHttpClient();

    protected String request(String url) {
        if (url == null || url.isEmpty()) {
            return "";
        }
        try {
            HttpGet request = new HttpGet(url);

            request.addHeader("User-Agent", HttpHeaders.USER_AGENT);
            HttpResponse response = client.execute(request);

            System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CExchangeException("We have some error with request data from Exchange", e);
        }
    }
}
