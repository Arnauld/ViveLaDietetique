package fr.viveladietetique.client.mvp;

import net.customware.gwt.presenter.client.widget.WidgetDisplay;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class GreetingHomeView extends Composite implements GreetingHomePresenter.Display {
	private final TextBox email;
	private final PasswordTextBox password;
	private final Hyperlink registerLink;
	private final Button connectButton;
	private final Label error;

	public GreetingHomeView() {
		connectButton = new Button("Se connecter");
		registerLink = new Hyperlink("Pas encore inscrit(e)", "baz");
		
		
		error = new Label();
		error.addStyleName("error");
		email = new TextBox();
		password = new PasswordTextBox();

		//initWidgetUsingVerticalPanel();
		initWidgetUsingDecoratedPanel();
		
		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		//RootPanel.get("dataContent").add(this);
		
		reset();
	}
	
	private void initWidgetUsingDecoratedPanel()
	{
		// Create a table to layout the form options
	    FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

	    int row = 0;
	    // Add a title to the form
	    layout.setHTML(row, 0, "Accéder à mon compte");
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    row++;
	    layout.setWidget(row, 0, error);
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    row++;
	    layout.setHTML  (row, 0, "<b>Email</b>");
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	    layout.setWidget(row, 1, email);
	    
	    row++;
	    layout.setHTML  (row, 0, "<b>Mot de passe</b>");
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
	    layout.setWidget(row, 1, password);
	    
	    row++;
	    FlexTable buttons = new FlexTable();
	    buttons.setCellSpacing(6);
	    buttons.setWidget(0, 0, connectButton);
	    buttons.setWidget(0, 1, registerLink);
	    layout.setWidget(row, 0, buttons);
	    cellFormatter.setColSpan(row, 0, 2);
	    cellFormatter.setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_CENTER);

	    // Wrap the content in a DecoratorPanel
	    DecoratorPanel decPanel = new DecoratorPanel();
	    decPanel.setWidget(layout);
	    initWidget(decPanel);
	}
	
	private void initWidgetUsingVerticalPanel()
	{
		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(connectButton);
		buttons.add(registerLink);
		
		final VerticalPanel panel = new VerticalPanel();
		initWidget(panel);
		panel.add(new  HTML("<b>Email</b>"));
		panel.add(email);
		panel.add(new  HTML("<b>Mot de passe</b>"));
		panel.add(password);
		panel.add(error);
		panel.add(buttons);

	}
	
	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getError()
	 */
	@Override
	public HasText getError() {
		return error;
	}
	
	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getLogin()
	 */
	public HasValue<String> getLogin() {
		return email;
	}
	
	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getPassword()
	 */
	@Override
	public HasValue<String> getPassword() {
		return password;
	}
	
	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getRegister()
	 */
	@Override
	public HasClickHandlers getRegister() {
		return registerLink;
	}

	/* (non-Javadoc)
	 * @see fr.viveladietetique.client.mvp.GreetingHomePresenter.Display#getSend()
	 */
	public HasClickHandlers getConnect() {
		return connectButton;
	}
	
	public void reset() {
		// Focus the cursor on the name field when the app loads
		DeferredCommand.addCommand(new Command() {
			@Override
			public void execute() {
				email.setFocus(true);
				email.selectAll();
			}
		});
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