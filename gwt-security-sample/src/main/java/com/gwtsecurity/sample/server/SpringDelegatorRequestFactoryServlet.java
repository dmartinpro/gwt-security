package com.gwtsecurity.sample.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.web.bindery.requestfactory.server.ExceptionHandler;
import com.google.web.bindery.requestfactory.server.RequestFactoryServlet;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;

/**
 * Custom RequestFactoryServlet
 * @author dmartin
 *
 */
public class SpringDelegatorRequestFactoryServlet extends RequestFactoryServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1039311047141665761L;

	public SpringDelegatorRequestFactoryServlet() {
		this(new SpringSecurityRequestFactoryExceptionHandler(), new SpringServiceLayerDecorator());
	}

	public SpringDelegatorRequestFactoryServlet(ExceptionHandler exceptionHandler, ServiceLayerDecorator... serviceDecorators) {
		super(exceptionHandler, serviceDecorators);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		super.doPost(request, response);
	}
}
