/**
 * 
 */
package com.gwtsecurity.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtsecurity.client.LoginHandler;

/**
 * A simple (demo) Widget to log in to the server.
 * @author dmartin
 *
 */
public class LoginDialogBox extends DialogBox implements LoginUICapabilities {

	private static final int MAX_LENGTH = 100;

	private TextBox loginTextBox = null;
	private PasswordTextBox passwordTextBox = null;
	private Label errorMessageLabel = null;

	/**
	 * @see com.gwtsecurity.client.ui.LoginUICapabilities#getLoginValue()
	 */
	@Override
	public String getLoginValue() {
		return loginTextBox.getText();
	}

	/**
	 * @see com.gwtsecurity.client.ui.LoginUICapabilities#getPasswordValue()
	 */
	@Override
	public String getPasswordValue() {
		return passwordTextBox.getText();
	}

	private TextBox getLoginTextBox() {
		if (loginTextBox == null) {
			loginTextBox = new TextBox();
			loginTextBox.setMaxLength(MAX_LENGTH);
		}
		return loginTextBox;
	}

	private TextBox getPasswordTextBox() {
		if (passwordTextBox == null) {
			passwordTextBox = new PasswordTextBox();
			passwordTextBox.setMaxLength(MAX_LENGTH);
		}
		return passwordTextBox;
	}

	private Label getErrorMessageLabel() {
		if (errorMessageLabel == null) {
			errorMessageLabel = new Label();
			errorMessageLabel.setWordWrap(true);
		}
		return errorMessageLabel;
	}

	public void setErrorMessage(final String message) {
		getErrorMessageLabel().setText(message);
	}

	public LoginDialogBox() {
		super();
		this.setAnimationEnabled(true);
		this.setGlassEnabled(true);
		this.setAutoHideEnabled(false);
		this.center();
		this.setModal(true);
		this.setTitle("Log in");
		this.setText("Please enter your credentials");

		final FlexTable grid = new FlexTable();

		final Label login = new Label("Login");
		grid.setWidget(0, 0, login);
		grid.setWidget(0, 1, getLoginTextBox());

		final Label password = new Label("Password");
		grid.setWidget(1, 0, password);
		grid.setWidget(1, 1, getPasswordTextBox());

		final Button button = new Button("Log me in");
		button.addClickHandler(new LoginHandler(this));
		grid.setWidget(2, 1, button);

		final Button hideButton = new Button("Later...");
		hideButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				LoginDialogBox.this.hide();
			}
		});
		grid.setWidget(2, 2, hideButton);
		grid.setWidget(4, 0, getErrorMessageLabel());

		this.add(grid);

	}

}
