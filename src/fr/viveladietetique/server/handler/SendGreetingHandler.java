package fr.viveladietetique.server.handler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;
import org.apache.commons.logging.Log;
import fr.viveladietetique.shared.rpc.SendGreeting;
import fr.viveladietetique.shared.rpc.SendGreetingResult;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class SendGreetingHandler implements ActionHandler<SendGreeting, SendGreetingResult> {
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public SendGreetingHandler(final Log logger,
				   final Provider<ServletContext> servletContext,				   
				   final Provider<HttpServletRequest> servletRequest) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#execute(net.customware.gwt.dispatch.shared.Action, net.customware.gwt.dispatch.server.ExecutionContext)
	 */
	public SendGreetingResult execute(final SendGreeting action,
					  final ExecutionContext context) throws ActionException {
		
		
		final String name = action.getName();
		 
		try {
			String serverInfo = servletContext.get().getServerInfo();
			
			String userAgent = servletRequest.get().getHeader("User-Agent");
			
			final String message = "Hello, " + name + "!<br><br>I am running " + serverInfo
					+ ".<br><br>It looks like you are using:<br>" + userAgent;

			
			//final String message = "Hello " + action.getName(); 
			
			return new SendGreetingResult(name, message);
		}
		catch (Exception cause) {
			logger.error("Unable to send message", cause);
			
			throw new ActionException(cause);
		}
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#rollback(net.customware.gwt.dispatch.shared.Action, net.customware.gwt.dispatch.shared.Result, net.customware.gwt.dispatch.server.ExecutionContext)
	 */
	public void rollback(final SendGreeting action,
			     final SendGreetingResult result,
			     final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}
	
	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#getActionType()
	 */
	public Class<SendGreeting> getActionType() {
		return SendGreeting.class;
	}
}