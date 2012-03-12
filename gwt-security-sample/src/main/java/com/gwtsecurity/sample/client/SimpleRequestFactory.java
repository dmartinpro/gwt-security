/**
 * 
 */
package com.gwtsecurity.sample.client;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.gwtsecurity.sample.client.domain.SimpleValueObjectProxy;
import com.gwtsecurity.sample.server.locator.RequestFactoryServerSideFacade;
import com.gwtsecurity.sample.server.locator.RequestFactorySpringLocator;


/**
 * @author dmartin
 *
 */
public interface SimpleRequestFactory extends RequestFactory {

	ValueObjectRequest valueObjectRequest();

	@Service(value=RequestFactoryServerSideFacade.class, locator=RequestFactorySpringLocator.class)
	public interface ValueObjectRequest extends RequestContext {

		Request<Void> test();

		Request<List<SimpleValueObjectProxy>> getSomeData();

		Request<SimpleValueObjectProxy> justTestValidation(SimpleValueObjectProxy incomingObject);

	}

}
