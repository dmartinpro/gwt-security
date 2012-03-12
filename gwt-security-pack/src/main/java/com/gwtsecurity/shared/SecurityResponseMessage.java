/**
 * 
 */
package com.gwtsecurity.shared;

/**
 * Interface of the security response messages the server can send.
 *
 * @author dmartin
 *
 */
public interface SecurityResponseMessage {

	int getStatus();

	void setStatus(int status);

	String getMessage();

	void setMessage(String message);

	String getLoginFormUrl();
	
	void setLoginFormUrl(String loginFormUrl);

}
