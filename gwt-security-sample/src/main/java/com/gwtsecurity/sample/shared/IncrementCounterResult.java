/**
 * 
 */
package com.gwtsecurity.sample.shared;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author dmartin
 *
 */
public class IncrementCounterResult implements Result {
    private int amount;
    private int current;

    /** For serialization only. */
    IncrementCounterResult() {
    }

    public IncrementCounterResult( int amount, int current ) {
        this.amount = amount;
        this.current = current;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrent() {
        return current;
    }
}