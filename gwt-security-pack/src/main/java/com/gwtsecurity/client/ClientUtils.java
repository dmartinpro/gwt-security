/**
 * 
 */
package com.gwtsecurity.client;

import com.google.gwt.core.client.GWT;

/**
 * @author dmartin
 *
 */
public class ClientUtils {

	/**
	 * Redirect the client to a relative URL
	 * @param url the relative URL
	 */
	public static void redirectRelative(String url) {
		if (url == null) {
			return;
		}

		final String path = (url.startsWith("/")) ? url.substring(1) : url;
		final String absoluteUrl = GWT.getHostPageBaseURL() + path;
		ClientUtils.redirect(absoluteUrl);
	}
	
    /**
     * Redirect the client to an absolute URL
     * @param url
     */
	public static native void redirect(String url) /*-{
    	$wnd.location = url;
	}-*/;

}
