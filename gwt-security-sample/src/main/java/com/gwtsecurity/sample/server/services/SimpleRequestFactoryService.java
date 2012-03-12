/**
 * 
 */
package com.gwtsecurity.sample.server.services;

import com.gwtsecurity.sample.server.domain.SimpleValueObject;

/**
 * @author dmartin
 *
 */
public interface SimpleRequestFactoryService {

	SimpleValueObject toUpperCase(final SimpleValueObject valueObject);
	
}
