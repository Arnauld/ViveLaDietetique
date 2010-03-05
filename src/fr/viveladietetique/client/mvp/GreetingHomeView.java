package fr.viveladietetique.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class GreetingHomeView extends Composite implements GreetingHomePresenter.Display {
	private final TextBox email;
	private final PasswordTextBox password;
	private final Hyperlink registerLink;
	private final Button connectButton;

	public GreetingHomeView() {
		final VerticalPanel panel = new VerticalPanel();
		initWidget(panel);

		connectButton = new Button("Go");
		registerLink = new Hyperlink("Pas encore inscrit(e)", "baz");
		
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(connectButton);
		buttons.add(registerLink);
		
		email = new TextBox();
		password = new PasswordTextBox();
		panel.add(new  HTML("<b>Email</b>"));
		panel.add(email);
		panel.add(new  HTML("<b>Mot de passe</b>"));
		panel.add(password);
		panel.add(buttons);
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("dataContent").add(this);
		//RootPanel.get("sendButtonContainer").add(connectButton);
		
		reset();
	}

	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getLogin()
	 */
	public HasValue<String> getLogin() {
		return email;
	}

	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getSend()
	 */
	public HasClickHandlers getConnect() {
		return connectButton;
	}
	
	public void reset() {
		// Focus the cursor on the name field when the app loads
		email.setFocus(true);
		email.selectAll();
	}

	/**
	 * Returns this widget as the {@link WidgetDisplay#asWidget()} value.
	 */
	public Widget asWidget() {
		return this;
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.presenter.client.Display#startProcessing()
	 */
	public void startProcessing() {
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.presenter.client.Display#stopProcessing()
	 */
	public void stopProcessing() {
	}
}