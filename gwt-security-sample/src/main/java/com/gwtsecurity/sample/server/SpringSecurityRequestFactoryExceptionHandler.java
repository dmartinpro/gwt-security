/**
 * 
 */
package com.gwtsecurity.sample.server;

import org.springframework.security.access.AccessDeniedException;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtsecurity.shared.SecurityServerFailure;


/**
 * Just a try for now...
 *
 * @author dmartin
 *
 */
public class SpringSecurityRequestFactoryExceptionHandler implements ExceptionHandler {

	/**
	 * @see com.google.web.bindery.requestfactory.server.ExceptionHandler#createServerFailure(java.lang.Throwable)
	 */
	@Override
	public ServerFailure createServerFailure(Throwable throwable) {
		ServerFailure failure = null;
		if (throwable == null) {
			failure = new ServerFailure("Server Error", null, null, true);
		} else {
			if (throwable instanceof AccessDeniedException) {
				failure = new SecurityServerFailure("Server Error: " + throwable.getMessage(), throwable.getClass().getName(), null, true);
			} else {
				failure = new ServerFailure("Server Error: " + throwable.getMessage(), throwable.getClass().getName(), null, true);
			}
		}
		return failure;
	}

}
