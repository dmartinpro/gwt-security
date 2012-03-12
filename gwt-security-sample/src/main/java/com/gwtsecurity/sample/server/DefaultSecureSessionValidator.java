package com.gwtsecurity.sample.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;

public class DefaultSecureSessionValidator implements SecureSessionValidator {

	@Override
	public boolean isValid(String sessionId, HttpServletRequest req) {

		boolean isValid = false;

		HttpSession session = req.getSession(false);

		if (session != null){

			if (sessionId != null && session.getId() != null){
				isValid = sessionId.equals(session.getId());
			}
		}

		return isValid;
	}

}
