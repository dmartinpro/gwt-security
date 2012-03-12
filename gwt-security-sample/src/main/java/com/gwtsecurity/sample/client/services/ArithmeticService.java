package com.gwtsecurity.sample.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("services/arithmetic")
public interface ArithmeticService extends RemoteService {

	int add(int a, int b);

	int multiply(int a, int b);

}
