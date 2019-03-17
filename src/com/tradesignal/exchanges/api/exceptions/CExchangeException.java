/**
 * <p>Title: FundCount, LLC</p>
 * <p>Description: FundCount project</p>
 * <p>Copyright: Copyright (c) 2001 FundCount, LLC</p>
 * <p>Company: FundCount, LLC</p>
 */
package com.tradesignal.exchanges.api.exceptions;

public class CExchangeException extends RuntimeException {
    public CExchangeException(String message) {
        super(message);
    }

    public CExchangeException(String message, Throwable cause) {
        super(message, cause);
    }
}