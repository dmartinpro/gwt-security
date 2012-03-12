package com.gwtsecurity.sample.server.services;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.springframework.security.access.annotation.Secured;

import com.gwtsecurity.sample.shared.IncrementCounter;
import com.gwtsecurity.sample.shared.IncrementCounterResult;

/**
 * Simple service coming from GWT Dispatch documentation :)
 * 
 * @author dmartin
 * 
 */
public class IncrementCounterHandler implements
		ActionHandler<IncrementCounter, IncrementCounterResult> {

	private int current = 0;

	public Class<IncrementCounter> getActionType() {
		return IncrementCounter.class;
	}

	@Secured("ROLE_ADMIN")
	public synchronized IncrementCounterResult execute(IncrementCounter action, ExecutionContext context) throws ActionException {
		current += action.getAmount();
		return new IncrementCounterResult(action.getAmount(), current);
	}

	public synchronized void rollback(IncrementCounter action, IncrementCounterResult result, ExecutionContext context) throws ActionException {
		// Reset to the previous value.
		current = result.getCurrent() - result.getAmount();
	}

}