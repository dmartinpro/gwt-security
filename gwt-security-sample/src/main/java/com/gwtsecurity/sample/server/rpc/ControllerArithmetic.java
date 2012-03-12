package com.gwtsecurity.sample.server.rpc;

import org.gwtwidgets.server.spring.GWTSpringController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.gwtsecurity.sample.client.services.ArithmeticService;

/**
 * 
 * @author dmartin
 *
 */
@Service("ControllerArithmetic")
public class ControllerArithmetic extends GWTSpringController implements ArithmeticService {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public ControllerArithmetic() {
		System.out.println("");
	}

	@Secured("ROLE_ADMIN")
	public int add(int a, int b) {
		return a + b;
	}

	public int multiply(int a, int b) {
		return a*b;
	}

}