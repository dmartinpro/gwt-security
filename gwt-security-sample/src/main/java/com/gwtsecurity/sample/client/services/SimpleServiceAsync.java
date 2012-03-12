package com.gwtsecurity.sample.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SimpleServiceAsync {

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.gwtsecurity.sample.client.services.SimpleService
     */
    void getData(AsyncCallback<String> callback);

}
