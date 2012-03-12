package com.gwtsecurity.sample.client;

import net.customware.gwt.dispatch.client.DefaultExceptionHandler;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.secure.CookieSecureSessionAccessor;
import net.customware.gwt.dispatch.client.secure.SecureDispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.gwtsecurity.client.SecuredRequestTransport;
import com.gwtsecurity.sample.client.services.SimpleService;
import com.gwtsecurity.sample.client.services.SimpleServiceAsync;

/**
 * 
 * @author dmartin
 *
 */
public final class ApplicationResourcesFactory {

	private static final ApplicationResourcesFactory FACTORY = new ApplicationResourcesFactory();

	private final SimpleServiceAsync simpleService = GWT.create(SimpleService.class);
	private final DispatchAsync dispatchAsync = new SecureDispatchAsync(new DefaultExceptionHandler(), new CookieSecureSessionAccessor("JSESSIONID"));

	private EventBus eventBus = new SimpleEventBus();
	private SimpleRequestFactory requestFactory = GWT.create(SimpleRequestFactory.class);

	private ApplicationResourcesFactory() {
		requestFactory.initialize(eventBus, new SecuredRequestTransport());
	}
	
	static ApplicationResourcesFactory getInstance() {
		return FACTORY;
	}

	public static DispatchAsync getDispatchAsyncService() {
		return getInstance().dispatchAsync;
	}

	public static SimpleServiceAsync getSimpleService() {
		return getInstance().simpleService;
	}

	public static SimpleRequestFactory getSimpleRequestFactory() {
		return getInstance().requestFactory;
	}

}
