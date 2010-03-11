package fr.viveladietetique.server.handler;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

import org.apache.commons.logging.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;

import fr.viveladietetique.server.core.AppUser;
import fr.viveladietetique.server.core.AppUserDao;
import fr.viveladietetique.shared.rpc.OpenSession;
import fr.viveladietetique.shared.rpc.OpenSessionResult;
import fr.viveladietetique.util.MapBuilder;
import groovy.lang.Writable;
import groovy.text.GStringTemplateEngine;
import groovy.text.Template;

public class OpenSessionHandler implements ActionHandler<OpenSession, OpenSessionResult> {
	
	private final AppUserDao appUserDao;
	private final Log logger;
	private final Provider<ServletContext> servletContext;
	private final Provider<HttpServletRequest> servletRequest;

	@Inject
	public OpenSessionHandler(final Log logger,
				   final Provider<ServletContext> servletContext,				   
				   final Provider<HttpServletRequest> servletRequest,
				   final AppUserDao appUserDao) {
		this.logger = logger;
		this.servletContext = servletContext;
		this.servletRequest = servletRequest;
		this.appUserDao     = appUserDao;
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#execute(net.customware.gwt.dispatch.shared.Action, net.customware.gwt.dispatch.server.ExecutionContext)
	 */
	public OpenSessionResult execute(
			final OpenSession action,
			final ExecutionContext context) throws ActionException
	{
		final String login = action.getLogin();
		final String passw = action.getPassword();

		try {
			AppUser user = appUserDao.findByLogin(login);
			if(user==null) {
				return new OpenSessionResult(false, "Login not found");
			}
				
			String serverInfo = servletContext.get().getServerInfo();
			String userAgent  = servletRequest.get().getHeader("User-Agent");
			
			GStringTemplateEngine engine = new GStringTemplateEngine();
			Template template = engine.createTemplate(new File("template/welcome.tpl"));
			Writable writable = 
				template.make(MapBuilder.build()
							.with("name", login)
							.with("serverInfo", serverInfo)
							.with("userAgent", userAgent)
							.getMap());
			
			final String message = writable.toString();
			
			return new OpenSessionResult(true, message);
		}
		catch (Exception cause) {
			logger.error("Unable to connect", cause);
			throw new ActionException(cause);
		}
	}

	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#rollback(net.customware.gwt.dispatch.shared.Action, net.customware.gwt.dispatch.shared.Result, net.customware.gwt.dispatch.server.ExecutionContext)
	 */
	public void rollback(final OpenSession action,
			     final OpenSessionResult result,
			     final ExecutionContext context) throws ActionException {
		// Nothing to do here
	}
	
	/* (non-Javadoc)
	 * @see net.customware.gwt.dispatch.server.ActionHandler#getActionType()
	 */
	public Class<OpenSession> getActionType() {
		return OpenSession.class;
	}

}
