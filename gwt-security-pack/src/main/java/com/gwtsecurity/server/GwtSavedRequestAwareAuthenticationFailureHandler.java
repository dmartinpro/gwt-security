/**
 * 
 */
package com.gwtsecurity.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;


/**
 * @author dmartin
 *
 */
public class GwtSavedRequestAwareAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		boolean isGwtRequest = HttpRequestUtils.containsHeaderStartingWith(request, "X-GWT");

		if (!isGwtRequest) {
			super.onAuthenticationFailure(request, response, exception);
			return;
		}

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed: " + exception.getMessage());

	}

}
