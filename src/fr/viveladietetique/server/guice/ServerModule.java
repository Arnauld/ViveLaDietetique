package fr.viveladietetique.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import org.apache.commons.logging.Log;
import fr.viveladietetique.server.handler.SendGreetingHandler;
import com.google.inject.Singleton;

/**
 * Module which binds the handlers and configurations
 *
 */
public class ServerModule extends ActionHandlerModule {

	@Override
	protected void configureHandlers() {
		bindHandler(SendGreetingHandler.class);
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}
}