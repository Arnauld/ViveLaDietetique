/* $Id$ */
package fr.viveladietetique.client.dispatch;

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.Result;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("dispatch")
public interface AuthDispatchService extends RemoteService
{
    Result execute(String sessionId, Action<?> action) throws Exception;
}
