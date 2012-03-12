/**
 * 
 */
package com.gwtsecurity.server;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Work in progress...
 * DO NOT USE RIGHT NOW
 *
 * @author dmartin
 *
 */
@Controller
@RequestMapping("/principal")
public class PrincipalController {

	 @RequestMapping(value="/name", method = RequestMethod.GET)
	 @ResponseBody
	 public String getName(final HttpServletRequest request, final Principal principal) {
        return (principal == null) ? null : principal.getName();
	 }

}
