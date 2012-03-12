/**
 * 
 */
package com.gwtsecurity.shared;

import net.customware.gwt.dispatch.shared.ServiceException;

/**
 * @author dmartin
 * 
 */
public class ServiceSecurityException extends ServiceException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2639783649521523012L;
	private int statusCode;

	public ServiceSecurityException() {
	}
	
	public ServiceSecurityException(String message) {
		super(message);
	}

	public ServiceSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceSecurityException(String message, int statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public ServiceSecurityException(Throwable cause) {
		super(cause);
	}

	/**
	 * @return the status
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatusCode(int status) {
		this.statusCode = status;
	}

}
