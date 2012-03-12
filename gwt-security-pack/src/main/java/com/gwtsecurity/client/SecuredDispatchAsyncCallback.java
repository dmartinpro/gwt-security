/**
 * 
 */
package com.gwtsecurity.client;

import java.util.logging.Level;

import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.gwtsecurity.shared.AccessDeniedServiceSecurityException;

/**
 * A new AsyncCallback that can handle security responses coming from GWT Dispatch requests.
 *
 * @author dmartin
 *
 */
public abstract class SecuredDispatchAsyncCallback<T> extends SecuredAsyncCallback<T> {

	@Override
	protected boolean isAccessDenied(final Throwable error) {
		boolean accessDenied = false;
		int statusCode = 0;
		if (error instanceof AccessDeniedServiceSecurityException) {
			statusCode =  ((AccessDeniedServiceSecurityException) error).getStatusCode();
		} else if (error instanceof StatusCodeException) {
			statusCode =  ((StatusCodeException) error).getStatusCode();
		}
		if (statusCode == Response.SC_FORBIDDEN) {
			super.onAccessDenied();
			accessDenied = true;
		} else {
			asyncCallbackLogger.log(Level.WARNING, "Non handled case. Status code received is: " + statusCode);
		}
		return accessDenied;
	}

}
