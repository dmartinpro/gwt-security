/**
 * 
 */
package com.gwtsecurity.sample.client.domain;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;
import com.gwtsecurity.sample.server.domain.SimpleValueObject;


/**
 * The client side proxy of the server side ValueObject
 *
 * @author dmartin
 *
 */
@ProxyFor(value=SimpleValueObject.class)
public interface SimpleValueObjectProxy extends ValueProxy {

	String getLastName();

//	@Size(min=3, max=20) // client side validation
	void setLastName(String lastName);

	String getFirstName();

	void setFirstName(String firstName);

	String getEmail();
	
	void setEmail(String email);

}
