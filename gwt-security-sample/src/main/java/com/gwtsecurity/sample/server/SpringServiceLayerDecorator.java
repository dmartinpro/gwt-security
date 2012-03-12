package com.gwtsecurity.sample.server;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * Try to find in the Spring Application Context the requested Locator or ServiceLocator
 *
 * @author dmartin
 *
 */
public class SpringServiceLayerDecorator extends ServiceLayerDecorator {

	private ApplicationContext context;

	private ApplicationContext getApplicationContext() {
		if (context == null) {
			context = WebApplicationContextUtils.getWebApplicationContext(SpringDelegatorRequestFactoryServlet.getThreadLocalServletContext());
		}
		return context;
	}

	@Override
	public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	@Override
	public <T extends ServiceLocator> T createServiceLocator(Class<T> clazz) {
		if (clazz == null)
			return super.createServiceLocator(clazz);

		T sl = getApplicationContext().getBean(clazz);
		return (sl == null) ? super.createServiceLocator(clazz) : sl;
	}

}
