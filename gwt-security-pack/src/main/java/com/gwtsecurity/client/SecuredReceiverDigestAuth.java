/**
 * 
 */
package com.gwtsecurity.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * EXPERIMENTAL : work in progress class : do not use
 *
 * @author dmartin
 *
 */
public abstract class SecuredReceiverDigestAuth<V> extends Receiver<V> {

	private static final String SPRINGSECURITY_ACCESSDENIEDEXCEPTION = "org.springframework.security.access.AccessDeniedException";

	@Override
	public final void onFailure(ServerFailure error) {
		if (error != null && SPRINGSECURITY_ACCESSDENIEDEXCEPTION.equals(error.getExceptionType())) {
			DialogBox accessDeniedBox = new DialogBox(true, true);
			accessDeniedBox.setTitle("Access Denied");
			Button logoutButton = new Button("Logout");
			logoutButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					try {
						RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, "/moduleA/services/logout");
						rb.setCallback(new RequestCallback() {

							@Override
							public void onResponseReceived(Request request, Response response) {
								response.getStatusCode();
							}

							@Override
							public void onError(Request request, Throwable exception) {
								exception.getMessage();
							}
						});
//						rb.setHeader("Authorization", "Digest username=\"user\", realm=\"TestGWT Realm\", nonce=\"\", uri=\"/gwtRequest\", response=\"\", qop=auth, nc=00000001, cnonce=\"\"");
//						rb.setHeader("Authorization", "Digest username=\"anonymous\"");
//						rb.setUser("anonymous");
//						rb.setPassword("anonymous");
						rb.send();
					} catch (RequestException e) {
						e.printStackTrace();
					}
				}
			});
			Label label = new Label("<b>Access denied</b> : your credentials don't allow you to perform this action");

			VerticalPanel vp = new VerticalPanel();
			vp.add(label);
			vp.add(logoutButton);

			accessDeniedBox.add(vp);

			accessDeniedBox.center();
			accessDeniedBox.setAnimationEnabled(true);
			accessDeniedBox.show();
		
		}
		onFilteredFailure(error);
	}

	/**
	 * 
	 * @param error
	 */
	public void onFilteredFailure(ServerFailure error) {
		super.onFailure(error);
	}

}
