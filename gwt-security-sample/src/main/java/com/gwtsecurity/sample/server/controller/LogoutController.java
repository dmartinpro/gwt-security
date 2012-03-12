/**
 * 
 */
package com.gwtsecurity.sample.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author dmartin
 *
 */
@Controller
public class LogoutController {

	@RequestMapping("logout")
	@ResponseStatus(value=HttpStatus.UNAUTHORIZED)
	public void logout() {
		System.out.println("Logging out...");
	}

}
