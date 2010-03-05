/* $Id$ */
package fr.viveladietetique.client.dispatch;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * An auth-aware implementation of {@link DispatchAsync}
 */
public class AuthDispatchAsync implements DispatchAsync
{

    private static final AuthDispatchServiceAsync realService = GWT.create(AuthDispatchService.class);

    public AuthDispatchAsync()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.turbomanage.gwt.client.dispatch.AppEngineSecureDispatchServiceAsync
     * #execute(A, com.google.gwt.user.client.rpc.AsyncCallback)
     */
    public <A extends Action<R>, R extends Result> void execute(final A action, final AsyncCallback<R> callback)
    {

        // Get AppEngine session ID
        String sessionId = Cookies.getCookie("ACSID");

        realService.execute(sessionId, action, new AsyncCallback<Result>()
        {
            public void onFailure(Throwable caught)
            {
                callback.onFailure(caught);
            }

            @SuppressWarnings("unchecked")
            public void onSuccess(Result result)
            {
                callback.onSuccess((R) result);
            }
        });
    }

}
