package com.gwtsecurity.sample.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwtsecurity.sample.client.services.SimpleService;

/**
 * The server side implementation of the simple RPC service.
 */
@SuppressWarnings("serial")
public class SimpleServiceImpl extends RemoteServiceServlet implements SimpleService {

	public String getData() {

		final String serverInfo = getServletContext().getServerInfo();
		final String userAgent = getThreadLocalRequest()
				.getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		return serverInfo + "<br>" + escapeHtml(userAgent);
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

}
