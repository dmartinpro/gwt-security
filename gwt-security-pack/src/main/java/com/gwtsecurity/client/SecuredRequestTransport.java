/**
 * 
 */
package com.gwtsecurity.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtsecurity.shared.SecurityServerFailure;

/**
 * Add a HTTP Header to help the browser identifying the GWT requests.<br>
 * Filter the responses and check for 401 and 403 status codes.
 *
 * @author dmartin
 * 
 */
public class SecuredRequestTransport extends DefaultRequestTransport {

	public static final String GWT_SECURED_REQUEST_HEADER = "X-GWT-Secured";
	public static final String GWT_SECURED_REQUEST_HEADER_VALUE = "SECURED";

	private static final String SERVER_ERROR = "Server Error";

	/**
	 * A separate logger for wire activity, which does not get logged by the
	 * remote log handler, so we avoid infinite loops. All log messages that
	 * could happen every time a request is made from the server should be
	 * logged to this logger.
	 */
	private static final Logger wireLogger = Logger.getLogger("WireActivityLogger");

	/**
	 * 
	 */
	@Override
	protected RequestCallback createRequestCallback(final TransportReceiver receiver) {
		return new RequestCallback() {

			public void onError(Request request, Throwable exception) {
				wireLogger.log(Level.SEVERE, SERVER_ERROR, exception);
				receiver.onTransportFailure(new ServerFailure(exception
						.getMessage()));
			}

			public void onResponseReceived(Request request, Response response) {
				wireLogger.finest("Response received");
				if (Response.SC_OK == response.getStatusCode()) {
					String text = response.getText();
					receiver.onTransportSuccess(text);
				} else if (Response.SC_FORBIDDEN == response.getStatusCode()
						|| Response.SC_UNAUTHORIZED == response.getStatusCode()) {
					final String message = SERVER_ERROR + " "
							+ response.getStatusCode() + " "
							+ response.getStatusText() + " "
							+ response.getText();
					wireLogger.severe(message);
					final SecurityServerFailure serverFailure = new SecurityServerFailure(message);
					serverFailure.setStatusCode(response.getStatusCode());
					receiver.onTransportFailure(serverFailure);
				}
			}
		};
	}

	@Override
	protected void configureRequestBuilder(final RequestBuilder builder) {
		super.configureRequestBuilder(builder);
		builder.setHeader(GWT_SECURED_REQUEST_HEADER, GWT_SECURED_REQUEST_HEADER_VALUE);
	}

}
