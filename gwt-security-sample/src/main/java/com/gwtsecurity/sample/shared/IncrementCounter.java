/**
 * 
 */
package com.gwtsecurity.sample.shared;

import net.customware.gwt.dispatch.shared.Action;

/**
 * @author dmartin
 *
 */
public class IncrementCounter implements Action<IncrementCounterResult> {
    private int amount;

    /** For serialization only. */
    IncrementCounter() {
    }

    public IncrementCounter( int amount ) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}