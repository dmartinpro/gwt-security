package com.gwtsecurity.sample.server.locator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.gwtsecurity.sample.client.SimpleRequestFactory.ValueObjectRequest;
import com.gwtsecurity.sample.server.domain.SimpleValueObject;
import com.gwtsecurity.sample.server.services.SimpleRequestFactoryService;


/**
 * @see ValueObjectRequest
 * @author dmartin
 *
 */
@Component
public class RequestFactoryServerSideFacade {

    @Autowired
    private SimpleRequestFactoryService simpleSampleService;

	public static void test() {
		System.out.println(".....MyValueObjectFacade............");
	}

	/**
	 * Service ne faisant pas recours à un service Spring pour répondre à la couche cliente.
	 * @return
	 */
	public List<SimpleValueObject> getSomeData() {

		List<SimpleValueObject> data = new ArrayList<SimpleValueObject>();
		SimpleValueObject obj1 = new SimpleValueObject();
		obj1.setFirstName("Jean");
		obj1.setLastName("VALJEAN");
		data.add(obj1);

		return data;
	}

	/**
	 * Service délégant à un service Spring
	 * @param incomingObject
	 * @return
	 */
	@Secured("ROLE_ADMIN")
	public SimpleValueObject justTestValidation(final SimpleValueObject incomingObject) {
		return this.simpleSampleService.toUpperCase(incomingObject);
	}

}
