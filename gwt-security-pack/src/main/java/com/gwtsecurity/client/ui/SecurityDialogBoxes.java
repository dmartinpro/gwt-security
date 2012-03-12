/**
 * 
 */
package com.gwtsecurity.client.ui;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;

/**
 * @author dmartin
 *
 */
public abstract class SecurityDialogBoxes {
	
	private SecurityDialogBoxes() {}

	public static class AlertBox extends DialogBox {

		private static final String TITLE = "Alert";
		
		public AlertBox(String message) {
			super();
			this.setAutoHideEnabled(true);
			this.setAnimationEnabled(true);
			this.setModal(true);
			this.setGlassEnabled(true);
			this.center();

			this.setTitle(TITLE);
			this.setText(TITLE);
			final Label label = new Label(message);
			this.add(label);
		}
	}
	
	public static class AccessDeniedBox extends DialogBox {

		public AccessDeniedBox() {
			super();
			this.setAnimationEnabled(true);
			this.setAutoHideEnabled(true);
			this.center();
			this.setModal(true);
			this.setGlassEnabled(true);

			final Label accessDeniedMessage = new Label("Access Denied");

			this.setTitle("Security Error");
			this.setText("Security Error");
			this.add(accessDeniedMessage);
		}
	}
	
}
