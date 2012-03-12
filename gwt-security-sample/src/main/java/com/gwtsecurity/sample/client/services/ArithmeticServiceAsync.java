package com.gwtsecurity.sample.client.services;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public interface ArithmeticServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.gwtsecurity.sample.client.services.ArithmeticService
     */
    void add( int a, int b, AsyncCallback<java.lang.Integer> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static ArithmeticServiceAsync instance;

        public static final ArithmeticServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (ArithmeticServiceAsync) GWT.create( ArithmeticService.class );
                ServiceDefTarget target = (ServiceDefTarget) instance;
                target.setServiceEntryPoint( GWT.getModuleBaseURL() + "services/arithmetic" );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }


	void multiply(int a, int b, AsyncCallback<Integer> callback);
}
