package fr.viveladietetique.server.guice;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;
import org.apache.commons.logging.Log;

import fr.viveladietetique.server.core.AppUserDao;
import fr.viveladietetique.server.core.impl.AppUserDaoMemImpl;
import fr.viveladietetique.server.handler.OpenSessionHandler;
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
		bindHandler(OpenSessionHandler.class);
		bind(AppUserDao.class).toInstance(new AppUserDaoMemImpl().initDefaults());
		bind(Log.class).toProvider(LogProvider.class).in(Singleton.class);
	}
}