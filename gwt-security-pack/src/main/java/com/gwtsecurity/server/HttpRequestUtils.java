/**
 * 
 */
package com.gwtsecurity.server;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.savedrequest.SavedRequest;


/**
 * Utility class
 * @author dmartin
 *
 */
public class HttpRequestUtils {

    private static final Log logger = LogFactory.getLog(HttpRequestUtils.class);

	/**
	 * Does at least one header's name starts with the second parameter ?
	 * @param request the request
	 * @param headerNameStartWith the string a header should start with. Can't be null.
	 * @return true or false
	 */
	@SuppressWarnings("unchecked")
	public static boolean containsHeaderStartingWith(final HttpServletRequest request, final String headerNameStartWith) {
		if (headerNameStartWith == null) {
			throw new IllegalArgumentException("headerNameStartWith must not be null");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("HttpRequestUtils looking for some specific headers...");
		}
		
	    final Enumeration<String> headers = request.getHeaderNames();
	    String header = null;
	    while (headers.hasMoreElements()) {
	    	header = headers.nextElement();
			if (logger.isDebugEnabled()) {
				logger.debug("  -> header: " + header);
			}
			if (header != null && header.toLowerCase().startsWith(headerNameStartWith.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Does at least one header's name starts with the second parameter ?
	 * @param request the request
	 * @param headerNameStartWith the string a header should start with. Can't be null.
	 * @return true or false
	 */
	public static boolean containsHeaderStartingWith(final SavedRequest request, final String headerNameStartWith) {
		if (headerNameStartWith == null) {
			throw new IllegalArgumentException("headerNameStartWith must not be null");
		}

		if (request == null || request.getHeaderNames() == null) {
			return false;
		}

		for (String header : request.getHeaderNames()) {
			if (header != null && header.startsWith(headerNameStartWith)) {
				return true;
			}
		}
		return false;
	}

}
