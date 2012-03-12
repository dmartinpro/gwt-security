package com.gwtsecurity.server;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

/**
 * 
 * @author dmartin
 *
 */
public class GwtSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	public GwtSavedRequestAwareAuthenticationSuccessHandler() {
		super.setRequestCache(new GwtHttpSessionRequestCache());
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws ServletException, IOException {

		boolean isGwtRequest = HttpRequestUtils.containsHeaderStartingWith(request, "X-GWT");

		if (!isGwtRequest) {
			
			super.onAuthenticationSuccess(request, response, authentication);
			return;
		}

		this.setRedirectStrategy(new RedirectStrategy() {

			@Override
			public void sendRedirect(HttpServletRequest request,
					HttpServletResponse response, String s) throws IOException {
				// do nothing, no redirect to make it working with extjs

			}
		});

		super.onAuthenticationSuccess(request, response, authentication);

		final HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);

		final Writer out = responseWrapper.getWriter();

		out.write("{success:true}");
		out.flush();
		out.close();
	}

	static final class GwtHttpSessionRequestCache extends HttpSessionRequestCache {

	    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
	    	if (!HttpRequestUtils.containsHeaderStartingWith(request, "X-GWT")) {
	    		super.saveRequest(request, response);
	    	}
	    }		
		
	    @Override
	    public SavedRequest getRequest(HttpServletRequest currentRequest,
	    		HttpServletResponse response) {

	    	SavedRequest savedRequest = super.getRequest(currentRequest, response);
	    	if (HttpRequestUtils.containsHeaderStartingWith(savedRequest, "X-GWT")) {
	    		return null;
	    	} else {
	    		return savedRequest;
	    	}
	    }
	}
	
}