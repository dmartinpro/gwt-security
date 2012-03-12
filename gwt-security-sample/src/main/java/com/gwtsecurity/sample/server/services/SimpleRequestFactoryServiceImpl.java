/**
 * 
 */
package com.gwtsecurity.sample.server.services;

import org.springframework.stereotype.Component;

import com.gwtsecurity.sample.server.domain.SimpleValueObject;


/**
 * @author dmartin
 *
 */
@Component("simpleSampleService")
public class SimpleRequestFactoryServiceImpl implements SimpleRequestFactoryService {

	/**
	 * @see com.gwtsecurity.sample.server.services.SimpleRequestFactoryService#toUpperCase(com.gwtsecurity.sample.server.domain.MyValueObject)
	 */
	@Override
	public SimpleValueObject toUpperCase(SimpleValueObject valueObject) {
		valueObject.setFirstName(valueObject.getFirstName().toUpperCase());
		valueObject.setLastName(valueObject.getLastName().toUpperCase());
		return valueObject;
	}

}
