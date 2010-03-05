/* $Id$ */
package fr.viveladietetique.server.dispatch;

import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import fr.viveladietetique.client.dispatch.AuthDispatchService;
import fr.viveladietetique.server.core.AppUser;
import fr.viveladietetique.shared.InvalidSessionException;
import fr.viveladietetique.shared.annotation.RoleGuard;
import fr.viveladietetique.shared.data.RoleOp;

/**
 * A servlet implementation of the {@link SecureDispatchService}. This verifies
 * that an AppEngine user is logged in and inspects the passed AppEngine
 * session ID to prevent CSRF/XSRF attacks
 *
 * Inspired from David M. Chandler
 */
@Singleton
public class AuthDispatchServiceServlet extends RemoteServiceServlet
        implements AuthDispatchService
{
    private static final long serialVersionUID = -1456388230348266500L;
    private static final Log LOG = LogFactory.getLog(AuthDispatchServiceServlet.class);

    private final Dispatch dispatch;
    private Provider<HttpServletRequest> req;

    @Inject
    public AuthDispatchServiceServlet(Dispatch dispatch, Provider<HttpServletRequest> req, Provider<ServletContext> context)
    {
        LOG.info("Initializing servlet: "+dispatch+".");
        this.dispatch = dispatch;
        this.req = req;
    }

    public Result execute(String clientSessionId, Action<?> action) throws Exception
    {
        LOG.info("Executing action: "+action+" using sessionId: "+clientSessionId+".");
        try
        {
            AppUser user = getCurrentUser();
            if(user.isAnonymous()) {
                LOG.info("No user connected");
            }
            else
            {
	            // User is logged in, now try to match session tokens
	    	    // to prevent CSRF
	            if(!isSessionValid(clientSessionId))
	                throw new InvalidSessionException();
            }
            
            if(canExecute(user, action)) {
            	return dispatch.execute(action);	
            }
            else {
            	throw new InvalidSessionException("Not authorized to execute action");
            }
            
        }
        catch (RuntimeException e)
        {
            LOG.warn("Internal error while executing " + action.getClass().getName()
                    + ": " + e.getMessage(), e);
            throw e;
        }
    }

    protected boolean canExecute(AppUser user, Action<?> action) {
    	RoleGuard guard = action.getClass().getAnnotation(RoleGuard.class);
    	if(guard==null) {
    		LOG.info("No guard found on: "+action);
        	return true;
    	}
    	RoleOp op = guard.op();
    	boolean isFullfilled = 
    		op.newMatcher()
    			.availables(user.getRoles())
    			.guardedBy(guard.roles())
    			.isFullfilled();

    	LOG.info("User does fullfills guard: "+op+" "+Arrays.toString(guard.roles())+": "+isFullfilled);
    	if(!isFullfilled) {
    	}
    	
    	return isFullfilled;
	}

	protected AppUser getCurrentUser () {
		AppUser user = null;
		HttpSession session = req.get().getSession(false);
    	if(session!=null)
    		user = (AppUser)session.getAttribute(AppUser.USER_KEY);
    	if(user==null)
    		user = AppUser.getAnonymous();
    	return user;
    }
    
    protected String getSessionId () {
    	String sessionId = "";
	    Cookie[] cookies = req.get().getCookies();
	    if(cookies==null)
	    	return sessionId;
	    
	    for (Cookie cookie : cookies)
	    {
	        if (cookie.getName().equals("ACSID"))
	        {
	            sessionId = cookie.getValue();
	            break;
	        }
	    }
	    return sessionId;
    }
    
    protected boolean isSessionValid(String clientSessionId) {
	    String sessionId = getSessionId();
	    
	    if(LOG.isDebugEnabled())
	    	LOG.debug("Comparing client sessionId: '"+clientSessionId+"' with server sessionId: '"+sessionId+"'");
	    
	    String serverName = req.get().getServerName();
	    // Skip check on localhost so we can test in AppEngine local dev env
	    if (("localhost".equals(serverName)) || (sessionId.equals(clientSessionId)))
	    {
	        return true;
	    }
	    else
	    {
	    	return false;
	    }
    }
}
