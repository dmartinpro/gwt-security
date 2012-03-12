/**
 * 
 */
package com.gwtsecurity.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Timer;
import com.gwtsecurity.client.ui.LoginUICapabilities;

/**
 * @author dmartin
 * 
 */
public class LoginHandler implements ClickHandler {

	private static final String DEFAULT_SPRING_LOGIN_URL = "j_spring_security_check";

	private LoginUICapabilities loginUIComponent;

	private String springLoginUrl = null;

	public LoginHandler(final LoginUICapabilities loginUIComponent) {
		if (loginUIComponent == null) {
			throw new IllegalArgumentException("A LoginUICapabilities object must be provided");
		}
		this.loginUIComponent = loginUIComponent;
	}

	private String getFilteredLoginValue() {
		String unfilteredValue = this.loginUIComponent.getLoginValue();
		return unfilteredValue;
	}

	private String getFilteredPasswordValue() {
		String unfilteredValue = this.loginUIComponent.getPasswordValue();
		return unfilteredValue;
	}

	public String getSpringLoginUrl() {
		if (this.springLoginUrl == null) {
			this.springLoginUrl = GWT.getHostPageBaseURL() + DEFAULT_SPRING_LOGIN_URL;
		}
		return springLoginUrl;
	}

	@Override
	public void onClick(ClickEvent event) {

		final String url = GWT.getModuleBaseURL() + getSpringLoginUrl();

		final RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, url);

		rb.setHeader("Content-Type", "application/x-www-form-urlencoded");
		rb.setHeader("X-GWT-Secured", "Logging...");
		// rb.setHeader("X-XSRF-Cookie", Cookies.getCookie("myCookieKey"));
		// TODO : work on this
		final StringBuilder sbParams = new StringBuilder(100);
		sbParams.append("j_username=");
		sbParams.append(getFilteredLoginValue());
		sbParams.append("&j_password=");
		sbParams.append(getFilteredPasswordValue());

		try {
			rb.sendRequest(sbParams.toString(), new RequestCallback() {

				@Override
				public void onResponseReceived(final Request request, final Response response) {

					int status = response.getStatusCode();
					if (status == Response.SC_OK) { // 200: everything's ok
						loginUIComponent.setErrorMessage("You are logged !");
						Timer t = new Timer() {

							@Override
							public void run() {
								loginUIComponent.hide();
							}
							
						};
						t.schedule(1000);
					} else if (status == Response.SC_UNAUTHORIZED) { // 401: oups...
						loginUIComponent.setErrorMessage("Oups... Wrong credentials !");
					} else { // something else ?
						loginUIComponent.setErrorMessage("Oups... Unexpected error (" + status + ")");
					}
				}

				@Override
				public void onError(final Request request, final Throwable exception) {
					loginUIComponent.setErrorMessage("Oups... " + exception.getMessage());
				}
			});
		} catch (RequestException exception) {
			loginUIComponent.setErrorMessage("Oups... " + exception.getMessage());
		}
	}

}
