/**
 * 
 */
package com.gwtsecurity.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.gwt.user.client.ui.PopupPanel;
import com.gwtsecurity.client.ui.LoginDialogBox;
import com.gwtsecurity.client.ui.SecurityDialogBoxes;
import com.gwtsecurity.client.ui.SecurityDialogBoxes.AccessDeniedBox;
import com.gwtsecurity.shared.SecurityResponseMessage;

/**
 * A new AsyncCallback that can handle security responses.
 *
 * @author dmartin
 *
 */
public abstract class SecuredAsyncCallback<T> implements AsyncCallback<T>, SecurityCallbackHandler {

	protected static final Logger asyncCallbackLogger = Logger.getLogger("SecuredAsyncCallback");

	protected boolean isNotAuthorized(final SecurityResponseMessage securityResponseMessage) {
		// If not logged, display the logging popup window and stop
		if (securityResponseMessage != null && securityResponseMessage.getStatus() == Response.SC_UNAUTHORIZED) {
			return true;
		}
		return false;
	}

	protected boolean isAccessDenied(final Throwable error) {
		boolean accessDenied = false;
		if (error instanceof StatusCodeException) {
			int statusCode =  ((StatusCodeException) error).getStatusCode();
			if (statusCode == Response.SC_FORBIDDEN) {
				accessDenied = true;
			} else {
				asyncCallbackLogger.log(Level.WARNING, "Non handled case. Status code received is: " + statusCode);
			}
		}
		return accessDenied;
	}

	/**
	 * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
	 */
	@Override
	public final void onFailure(final Throwable caught) {

		if (caught == null) {
			asyncCallbackLogger.log(Level.FINEST, "Received parameter 'caught' is null ?!!");
			return;
		}

		final SecurityResponseMessage message = SecurityCallbackUtils.extractJsonMessage(caught.getMessage());

		// If not logged, display the logging popup window and stop
		if (isNotAuthorized(message)) {
			onAuthorizationExpected(message.getLoginFormUrl());
			return;
		}

		// If access is denied, display an error message box (AccessDeniedBox)
		if (isAccessDenied(caught)) {
			onAccessDenied();
			return;
		}

		onServiceFailure(caught);
	}

	/* (non-Javadoc)
	 * @see com.gwtsecurity.client.SecurityCallbackHandler#onAuthorizationExpected(java.lang.String)
	 */
	@Override
	public void onAuthorizationExpected(final String externalLoginUrl) {
		if (externalLoginUrl == null) {
			new LoginDialogBox().show();
		} else { // redirect
			final SecurityDialogBoxes.AlertBox box = new SecurityDialogBoxes.AlertBox("You are not logged : you will be redirected");
			box.addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(CloseEvent<PopupPanel> event) {
					ClientUtils.redirectRelative(externalLoginUrl);
				}
			});
			box.show();
		}
		
	}

	/* (non-Javadoc)
	 * @see com.gwtsecurity.client.SecurityCallbackHandler#onAccessDenied()
	 */
	@Override
	public void onAccessDenied() {
		new AccessDeniedBox().show();
	}

	public abstract void onServiceFailure(final Throwable caught);

}
