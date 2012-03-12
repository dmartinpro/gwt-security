/**
 * 
 */
package com.gwtsecurity.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtsecurity.client.ui.LoginDialogBox;
import com.gwtsecurity.client.ui.SecurityDialogBoxes;
import com.gwtsecurity.client.ui.SecurityDialogBoxes.AccessDeniedBox;
import com.gwtsecurity.shared.SecurityResponseMessage;
import com.gwtsecurity.shared.SecurityServerFailure;

/**
 * A "secured" Receiver that can handle security responses.<br>
 * Possible use cases :<br>
 * <ul>
 *  <li>Non authenticated: the (server) filter returns an error (HttpStatus.UNAUTHORIZED.value() = 401) => parsing in order to extract the (JSON) message</li>
 *  <li>Access denied: an AccessDeniedException is raised : the user is prompted he can't access the resource</li>
 *  <li>Everything's ok: onSuccess() method is called since permissions are ok with the requested resource</li>
 * </ul>
 * 
 * @author dmartin
 * 
 */
public abstract class SecuredReceiver<V> extends Receiver<V> implements SecurityCallbackHandler {

	private static final String SPRINGSECURITY_ACCESSDENIEDEXCEPTION = "org.springframework.security.access.AccessDeniedException";

	private static final Logger securedReceiverLogger = Logger.getLogger("SecuredReceiver");

	protected boolean isNotAuthorized(final SecurityResponseMessage securityResponseMessage) {
		// If not logged, display the logging popup window and stop
		if (securityResponseMessage != null && securityResponseMessage.getStatus() == Response.SC_UNAUTHORIZED) {
			if (securityResponseMessage.getLoginFormUrl() == null) {
				new LoginDialogBox().show();
			} else { // redirect
				final SecurityDialogBoxes.AlertBox box = new SecurityDialogBoxes.AlertBox("You are not logged : you will be redirected");
				box.addCloseHandler(new CloseHandler<PopupPanel>() {
					@Override
					public void onClose(CloseEvent<PopupPanel> event) {
						ClientUtils.redirectRelative(securityResponseMessage.getLoginFormUrl());
					}
				});
				box.show();
			}
			return true;
		}
		return false;
	}

	protected boolean isAccessDenied(final ServerFailure error) {
		boolean accessDenied = false;
		if (error instanceof SecurityServerFailure) {
			accessDenied =  (((SecurityServerFailure) error).getStatusCode() == Response.SC_FORBIDDEN);
		} else if (SPRINGSECURITY_ACCESSDENIEDEXCEPTION.equals(error.getExceptionType())) {
			accessDenied = true;
		}
		return accessDenied;
	}

	@Override
	public final void onFailure(final ServerFailure error) {

		if (error == null) {
			securedReceiverLogger.log(Level.FINEST, "ServerFailure is null");
			return;
		}

		final SecurityResponseMessage message = SecurityCallbackUtils.extractJsonMessage(error.getMessage());

		// If not logged, display the logging popup window (could redirect to a login page too ?) and stop
		if (isNotAuthorized(message)) {
			onAuthorizationExpected(message.getLoginFormUrl());
			return;
		}

		// If access is denied, display an error message box
		if (isAccessDenied(error)) {
			onAccessDenied();
			return;
		}

		// then let the user's code be executed
		onServiceFailure(error);
	}

	/**
	 * This is the new method to use instead of onFailure with a classic Receiver object.
	 * @param error
	 */
	public void onServiceFailure(ServerFailure error) {
		super.onFailure(error);
	}

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

	@Override
	public void onAccessDenied() {
		new AccessDeniedBox().show();
	}

}
