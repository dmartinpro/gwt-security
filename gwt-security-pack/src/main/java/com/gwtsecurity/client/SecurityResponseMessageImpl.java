/**
 * 
 */
package com.gwtsecurity.client;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.gwtsecurity.shared.SecurityResponseMessage;

/**
 * Simple implementation with a static builder method.<br>
 * 
 * Used when AutoBean is not involved to decode a JSON security response message.
 * 
 * @author dmartin
 *
 */
public class SecurityResponseMessageImpl implements SecurityResponseMessage {

	private String message;

	private int status;

	private String loginFormUrl;


	public static final SecurityResponseMessage getSecurityResponseMessage(String json) {
		final JSONValue jsonValue = JSONParser.parseStrict(json);
		final JSONObject object = jsonValue.isObject();
		SecurityResponseMessage responseMsg = null;
		if (object != null) {
			final JSONValue statusValue = object.get("status");
			if (statusValue != null) {
				final JSONValue messageValue = object.get("message");
				final JSONValue loginFormUrlValue = object.get("loginFormUrl");
				final SecurityResponseMessageImpl msg = new SecurityResponseMessageImpl();
				msg.setStatus(statusValue.isNumber());
				msg.setMessage(messageValue.isString());
				msg.setLoginFormUrl((loginFormUrlValue == null) ? null : loginFormUrlValue.isString());
				responseMsg = msg;
			}
		}
		return responseMsg;
	}

	/**
	 * @see com.gwtsecurity.shared.SecurityResponseMessage#getStatus()
	 */
	@Override
	public int getStatus() {
		return status;
	}

	/**
	 * @see com.gwtsecurity.shared.SecurityResponseMessage#setStatus(int)
	 */
	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public void setStatus(JSONNumber jsonNumber) {
		this.status = (jsonNumber == null) ? 0 : Double.valueOf(jsonNumber.doubleValue()).intValue();
	}

	/**
	 * @see com.gwtsecurity.shared.SecurityResponseMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		return this.message;
	}

	/**
	 * @see com.gwtsecurity.shared.SecurityResponseMessage#setMessage(java.lang.String)
	 */
	@Override
	public void setMessage(String message) {
		this.message = message;
	}

	public void setMessage(JSONString jsonString) {
		this.message = (jsonString == null) ? null : jsonString.stringValue();
	}

	/**
	 * @return the loginFormUrl
	 */
	public String getLoginFormUrl() {
		return loginFormUrl;
	}

	/**
	 * @param loginFormUrl the loginFormUrl to set
	 */
	public void setLoginFormUrl(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	/**
	 * @param loginFormUrl the loginFormUrl to set
	 */
	public void setLoginFormUrl(JSONString jsonString) {
		this.loginFormUrl = (jsonString == null) ? null : jsonString.stringValue();
	}

}
