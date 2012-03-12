/**
 * 
 */
package com.gwtsecurity.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;

/**
 * @author dmartin
 * 
 */
@UiTemplate("LogoutButton")
public class LogoutButton extends Button {

	private static final String DEFAULT_SPRING_LOGOUT_URL = "j_spring_security_logout";
	private static final String DEFAULT_BUTTON_TEXT = "Log out";

	private String logoutUrl = null;

	public LogoutButton() {
		this(DEFAULT_BUTTON_TEXT);
	}

	public LogoutButton(String html) {
		this.setHTML(html);
		this.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				final RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, getLogoutUrl());
				// rb.setHeader("Accept", "application/json"); // json expected ?
				rb.setCallback(new RequestCallback() {

					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							new SecurityDialogBoxes.AlertBox("You are now logged out").show();
						} else {
							new SecurityDialogBoxes.AlertBox("Something unexpected occured (status code:" + response.getStatusCode() + ")").show();
						}

					}

					@Override
					public void onError(Request request, Throwable exception) {
						new SecurityDialogBoxes.AlertBox("Something unexpected occured (" + exception.getMessage() + ")").show();
					}
				});
				try {
					rb.send();
				} catch (RequestException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getLogoutUrl() {
		if (logoutUrl == null) {
			logoutUrl = GWT.getHostPageBaseURL() + DEFAULT_SPRING_LOGOUT_URL;
		}
		return logoutUrl;
	}

	public void setLogoutUrl(final String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

}
