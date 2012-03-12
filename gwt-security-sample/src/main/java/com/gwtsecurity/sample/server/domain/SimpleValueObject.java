package com.gwtsecurity.sample.server.domain;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

/**
 * Just a simple server side ValueObject
 *
 * @author dmartin
 *
 */
public class SimpleValueObject {

	@Size(min=3, max=20)
	private String lastName;

	@Size(min=3, max=20)
	private String firstName;

	@Email
	private String email;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
