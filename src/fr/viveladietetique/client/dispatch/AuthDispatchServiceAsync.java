/* $Id$ */
package fr.viveladietetique.client.dispatch;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AuthDispatchServiceAsync
{
    void execute(String sessionId, Action<?> action, AsyncCallback<Result> callback);
}
