package fr.viveladietetique.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.DisplayCallback;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.place.Place;
import net.customware.gwt.presenter.client.place.PlaceRequest;
import net.customware.gwt.presenter.client.widget.WidgetDisplay;
import net.customware.gwt.presenter.client.widget.WidgetPresenter;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.inject.Inject;

import fr.viveladietetique.shared.event.SessionOpenedEvent;
import fr.viveladietetique.shared.event.GreetingSentEvent;
import fr.viveladietetique.shared.rpc.OpenSession;
import fr.viveladietetique.shared.rpc.OpenSessionResult;
import fr.viveladietetique.shared.rpc.SendGreetingResult;

public class GreetingHomePresenter extends WidgetPresenter<GreetingHomePresenter.Display>
	
{
    /**
     * The message displayed to the user when the server cannot be reached or
     * returns an error.
     */
    private static final String SERVER_ERROR = 
              "An error occurred while attempting to contact the server. "
            + "Please check your network connection and try again.";

    public interface Display extends WidgetDisplay
    {
    	public HasValue<String> getLogin();
        public HasValue<String> getPassword();
        public HasText          getError();
        public HasClickHandlers getConnect();
        public HasClickHandlers getRegister();
    }

    public static final Place PLACE = new Place("Greeting");

    private final DispatchAsync dispatcher;

    // FUDGE FACTOR! Although this is not used, having GIN pass the object
    // to this class will force its instantiation and therefore will make the
    // response presenter listen for events (via bind()). This is not a very
    // good way to
    // achieve this, but I wanted to put something together quickly - sorry!
    @SuppressWarnings("unused")
    private final GreetingResponsePresenter greetingResponsePresenter;

    @Inject
    public GreetingHomePresenter(
    		final Display display, 
    		final EventBus eventBus,
    		final DispatchAsync dispatcher,
            final GreetingResponsePresenter greetingResponsePresenter)
    {
        super(display, eventBus);
        this.dispatcher = dispatcher;
        this.greetingResponsePresenter = greetingResponsePresenter;

        bind();
    }
    
    @Override
    protected void onBind()
    {
    	// 'display' is a final global field containing the Display passed into
    	// the constructor.
    	display.getConnect().addClickHandler(new ClickHandler()
    	{
    		public void onClick(final ClickEvent event)
    		{
    			doConnect();
    		}
    	});
    	display.getRegister().addClickHandler(new ClickHandler()
    	{
    		public void onClick(final ClickEvent event)
    		{
    			//doRegister();
    		}
    	});
    }
    
    @Override
    protected void onUnbind()
    {
    	// Add unbind functionality here for more complex presenters.
    }

    /**
     * Try to connect
     */
    private void doConnect()
    {
    	Log.info("Calling doConnect");
    	
    	// reset any error display
    	display.getError().setText("");
    	
    	// login infos.
    	String name = display.getLogin().getValue();
    	String pass = display.getPassword().getValue();
    	
    	dispatcher.execute(new OpenSession(name,pass), createConnectCallback());
    }
    
    private DisplayCallback<OpenSessionResult> createConnectCallback()
    {
        return new DisplayCallback<OpenSessionResult>(display)
        {
            @Override
            protected void handleFailure(final Throwable cause)
            {
                Log.error("Handle Failure [GreetingHomePresenter]: " + cause.getLocalizedMessage(), cause);
                Window.alert(SERVER_ERROR+"\n"+cause.getLocalizedMessage());
            }

            @Override
            protected void handleSuccess(final OpenSessionResult result)
            {
            	if(result.isConnected()) {
	                // take the result from the server and notify client interested
	                // components
	                eventBus.fireEvent(new SessionOpenedEvent(result.getName(), result.getMessage()));
            	}else{
            		display.getError().setText(result.getFailureReason());
            	}
            }
        };
    }
    
    private DisplayCallback<SendGreetingResult> createCallback()
    {
        return new DisplayCallback<SendGreetingResult>(display)
        {
            @Override
            protected void handleFailure(final Throwable cause)
            {
                Log.error("Handle Failure [GreetingHomePresenter]: " + cause.getLocalizedMessage(), cause);
                Window.alert(SERVER_ERROR);
            }

            @Override
            protected void handleSuccess(final SendGreetingResult result)
            {
                // take the result from the server and notify client interested
                // components
                eventBus.fireEvent(new GreetingSentEvent(result.getName(), result.getMessage()));
            }
        };
    }

    public void refreshDisplay()
    {
        // This is called when the presenter should pull the latest data
        // from the server, etc. In this case, there is nothing to do.
    }

    public void revealDisplay()
    {
        // Nothing to do. This is more useful in UI which may be buried
        // in a tab bar, tree, etc.
    }

    /**
     * Returning a place will allow this presenter to automatically trigger when
     * '#Greeting' is passed into the browser URL.
     */
    @Override
    public Place getPlace()
    {
        return PLACE;
    }

    @Override
    protected void onPlaceRequest(final PlaceRequest request)
    {
        // Grab the 'name' from the request and put it into the 'name' field.
        // This allows a tag of '#Greeting;name=Foo' to populate the name
        // field.
        final String name = request.getParameter("name", null);

        if (name != null)
        {
            display.getLogin().setValue(name);
        }
    }
}