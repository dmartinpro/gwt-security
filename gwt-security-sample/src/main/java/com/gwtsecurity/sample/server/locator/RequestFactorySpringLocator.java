/**
 * 
 */
package com.gwtsecurity.sample.server.locator;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * @author dmartin
 *
 */
@Component
public class RequestFactorySpringLocator implements ServiceLocator, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public Object getInstance(Class<?> clazz) {
		return (clazz == null) ? null : applicationContext.getBean(clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

}
