package com.gwtsecurity.client;

public interface SecurityCallbackHandler {

	void onAuthorizationExpected(final String externalLoginUrl);

	void onAccessDenied();

}