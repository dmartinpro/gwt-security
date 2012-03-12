package com.gwtsecurity.client.ui;

public interface LoginUICapabilities {

	String getLoginValue();

	String getPasswordValue();

	void setErrorMessage(final String message);
	
	void hide();
}