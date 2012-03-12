/**
 * 
 */
package com.gwtsecurity.shared;

import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * 
 * @author dmartin
 *
 */
public class SecurityServerFailure extends ServerFailure {

	private static final String DEFAULT_MESSAGE = "Security Failure. Check your credentials";
	private int statusCode;

	/**
	 * 
	 */
	public SecurityServerFailure() {
		this(DEFAULT_MESSAGE);
	}

	/**
	 * @param message
	 */
	public SecurityServerFailure(String message) {
		super(message);
	}

	/**
	 * @param message
	 * @param exceptionType
	 * @param stackTraceString
	 * @param fatal
	 */
	public SecurityServerFailure(String message, String exceptionType, String stackTraceString, boolean fatal) {
		super(message, exceptionType, stackTraceString, fatal);
	}

	/**
	 * @return the status
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatusCode(int status) {
		this.statusCode = status;
	}

}
