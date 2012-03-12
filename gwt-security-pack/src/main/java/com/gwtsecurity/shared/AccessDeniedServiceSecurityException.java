/**
 * 
 */
package com.gwtsecurity.shared;


/**
 * @author dmartin
 * 
 */
public class AccessDeniedServiceSecurityException extends ServiceSecurityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4829017012472741343L;

	public AccessDeniedServiceSecurityException() {
	}

	public AccessDeniedServiceSecurityException(String message) {
		super(message);
	}

	public AccessDeniedServiceSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedServiceSecurityException(String message, int statusCode) {
		super(message, statusCode);
	}

	public AccessDeniedServiceSecurityException(Throwable cause) {
		super(cause);
	}

}
