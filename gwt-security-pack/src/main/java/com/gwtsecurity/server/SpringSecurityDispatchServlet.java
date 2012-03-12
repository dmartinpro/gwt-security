/**
 * 
 */
package com.gwtsecurity.server;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.secure.SecureSessionValidator;
import net.customware.gwt.dispatch.server.spring.SpringSecureDispatchServlet;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.Result;
import net.customware.gwt.dispatch.shared.ServiceException;
import net.customware.gwt.dispatch.shared.secure.InvalidSessionException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import com.gwtsecurity.shared.AccessDeniedServiceSecurityException;

/**
 * @author dmartin
 *
 */
public class SpringSecurityDispatchServlet extends SpringSecureDispatchServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8895427905400456131L;

	public Result execute( String sessionId, Action<?> action ) throws DispatchException {
        try {
            SecureSessionValidator sessionValidator = getSessionValidator();
            if ( sessionValidator == null )
                throw new ServiceException("No session validator found for servlet '" + getServletName() + "' . Please verify your server-side configuration.");
            
            Dispatch dispatch = getDispatch();
            if ( dispatch == null )
                throw new ServiceException("No dispatch found for servlet '" + getServletName() + "' . Please verify your server-side configuration.");
            
            if ( sessionValidator.isValid( sessionId, getThreadLocalRequest() ) ) {
                return dispatch.execute( action );
            } else {
                throw new InvalidSessionException();
            }
        } catch ( AccessDeniedException e ) {
            log( "AccessDeniedException while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw new AccessDeniedServiceSecurityException( e.getMessage(), HttpStatus.FORBIDDEN.value());
        } catch ( RuntimeException e ) {
            log( "Exception while executing " + action.getClass().getName() + ": " + e.getMessage(), e );
            throw new ServiceException( e.getMessage() );
        }
    }

}
