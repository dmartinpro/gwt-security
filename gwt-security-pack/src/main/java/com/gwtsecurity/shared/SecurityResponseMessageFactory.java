/**
 * 
 */
package com.gwtsecurity.shared;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

/**
 * The AutoBean Factory
 * @author dmartin
 *
 */
public interface SecurityResponseMessageFactory extends AutoBeanFactory {

	AutoBean<SecurityResponseMessage> securityResponseMessage();
	
	final class Builder {

		private static final SecurityResponseMessageFactory FACTORY = GWT.create(SecurityResponseMessageFactory.class);

		public static final SecurityResponseMessage getSecurityResponseMessage(final String json) {
			final AutoBean<SecurityResponseMessage> bean = AutoBeanCodex.decode(FACTORY, SecurityResponseMessage.class, json);
			return bean.as();
		}
		
	}
}
