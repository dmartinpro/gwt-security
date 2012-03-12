/**
 * 
 */
package com.gwtsecurity.sample.client.view;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.ServerFailure;
import com.gwtsecurity.client.SecuredAsyncCallback;
import com.gwtsecurity.client.SecuredDispatchAsyncCallback;
import com.gwtsecurity.client.SecuredReceiver;
import com.gwtsecurity.sample.client.ApplicationResourcesFactory;
import com.gwtsecurity.sample.client.SimpleRequestFactory;
import com.gwtsecurity.sample.client.domain.SimpleValueObjectProxy;
import com.gwtsecurity.sample.client.services.ArithmeticServiceAsync;
import com.gwtsecurity.sample.shared.IncrementCounter;
import com.gwtsecurity.sample.shared.IncrementCounterResult;

/**
 * @author dmartin
 * 
 */
public class SimpleView extends Composite {

	interface SimpleUiBinder extends UiBinder<Widget, SimpleView> {
	}

	private static SimpleUiBinder uiBinder = GWT.create(SimpleUiBinder.class);

	@UiField HasText principalLabel;

	@UiField HasText value1;
	@UiField HasText value2;

	@UiField HasText firstNameField;
	@UiField HasText lastNameField;
	@UiField HasText emailField;

	@UiField HasText errorLabel;

	public SimpleView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		refreshUserName();
	}

	public void refreshUserName() {
		final String url = GWT.getHostPageBaseURL() + "moduleA/services/principal/name";

		final RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url);
		rb.setCallback(new RequestCallback() {

			@Override
			public void onResponseReceived(com.google.gwt.http.client.Request request, Response response) {
				if (response.getStatusCode() == Response.SC_OK) {
					String welcomeMsg = "You are not logged";
					if (response.getText() != null && response.getText().length() > 0) {
						welcomeMsg = "Welcome: " + response.getText();
					}
					principalLabel.setText(welcomeMsg);
				}
			}

			@Override
			public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
				principalLabel.setText("Can't retrieve principal's name");
			}
		});
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}

	@UiHandler("gwtRPCButton")
	public void onRpcButtonClick(ClickEvent e) {
		ApplicationResourcesFactory.getSimpleService().getData(new SecuredAsyncCallback<String>() {
			public void onServiceFailure(Throwable caught) {
				// Show the RPC error message to the user
				DialogBox dialogBox = new DialogBox();
				dialogBox.center();
				dialogBox.setModal(true);
				dialogBox.setGlassEnabled(true);
				dialogBox.setAutoHideEnabled(true);

				dialogBox.setText("Remote Procedure Call - Failure");				
				dialogBox.show();
			}

			public void onSuccess(String result) {
				DialogBox dialogBox = new DialogBox();
				dialogBox.center();
				dialogBox.setModal(true);
				dialogBox.setGlassEnabled(true);
				dialogBox.setAutoHideEnabled(true);

				dialogBox.setText(result);
				dialogBox.show();
			}
		});
		
	}
	
	@UiHandler("gwtSlButton")
	public void onGwtSlButtonClick(ClickEvent e) {
			String firstValueAsText = value1.getText();
			String secondValueAsText = value2.getText();

			firstValueAsText = (firstValueAsText == null || firstValueAsText.length() == 0) ? "0" : firstValueAsText;
			secondValueAsText = (secondValueAsText == null || secondValueAsText.length() == 0) ? "0" : secondValueAsText;

			int firstValueAsInt = 0;
			int secondValueAsInt = 0;
			
			try {
				firstValueAsInt = Integer.parseInt(firstValueAsText);
			} catch (NumberFormatException nfe) {
				firstValueAsInt = 0;
			}

			try {
				secondValueAsInt = Integer.parseInt(secondValueAsText);
			} catch (NumberFormatException nfe) {
				secondValueAsInt = 0;
			}

			ArithmeticServiceAsync.Util.getInstance().add(firstValueAsInt, secondValueAsInt, new SecuredAsyncCallback<Integer>() {
				public void onSuccess(Integer arg0) {
					final DialogBox dialogBox = new DialogBox();
					dialogBox.setHTML("<b>Here is the result</b> :" + arg0);
					dialogBox.setAutoHideEnabled(true);
					dialogBox.center();
					dialogBox.show();
				}

				public void onServiceFailure(Throwable error) {
					errorLabel.setText("ERROR: " + error);
				}
			});
	}

	@UiHandler("dispatchButton")
	public void onDispatchButtonClick(ClickEvent e) {
		ApplicationResourcesFactory.getDispatchAsyncService().execute( new IncrementCounter( 1 ), new SecuredDispatchAsyncCallback<IncrementCounterResult>() {
            public void onServiceFailure( Throwable e ) {
                Window.alert( "Error: " + e.getMessage() );
            }

            public void onSuccess( IncrementCounterResult result ) {
                Window.alert( "Incremented by " + result.getAmount() + ", new total is " + result.getCurrent() );
            }

        } );

	}
	
	@UiHandler("rfButton")
	public void onRfButtonClick(ClickEvent event) {
		SimpleRequestFactory.ValueObjectRequest request = ApplicationResourcesFactory.getSimpleRequestFactory().valueObjectRequest();
		SimpleValueObjectProxy proxy = request.create(SimpleValueObjectProxy.class);

		proxy.setFirstName(firstNameField.getText());
		proxy.setLastName(lastNameField.getText());
		proxy.setEmail(emailField.getText());

		Request<SimpleValueObjectProxy> req1 = request.justTestValidation(proxy);

		Receiver<SimpleValueObjectProxy> receiver = new SecuredReceiver<SimpleValueObjectProxy>() {
			@Override
			public void onSuccess(SimpleValueObjectProxy response) {
				final DialogBox box = new DialogBox();
				box.setAutoHideEnabled(true);
				box.setText("OK ! Let's see the service output : " + response.getFirstName() + " " + response.getLastName());
				box.center();
				box.show();
			}
			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {

				super.onConstraintViolation(violations);

				final StringBuffer sb = new StringBuffer("<ul>");
				for (ConstraintViolation<?> violation : violations) {
					sb.append("<li>").append(violation.getPropertyPath()).append(" : ").append(violation.getMessage()).append("</li>");
				}
				sb.append("</ul>");
				final DialogBox box = new DialogBox();
				box.setAutoHideEnabled(true);
				box.setText("CONSTRAINTS VIOLATED");
				box.add(new HTML(sb.toString()));
				box.center();
				box.show();
			}

			@Override
			public void onServiceFailure(ServerFailure error) {
				errorLabel.setText("ERROR ! " + error.getMessage());
			}
		};

		req1.to(receiver);
		request.fire();
		
	}
}
