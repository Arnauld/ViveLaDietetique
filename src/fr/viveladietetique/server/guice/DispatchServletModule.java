package fr.viveladietetique.server.guice;

import com.google.inject.servlet.ServletModule;

import fr.viveladietetique.server.dispatch.AuthDispatchServiceServlet;

public class DispatchServletModule extends ServletModule {

	@Override
	public void configureServlets() {
		// NOTE: the servlet context will probably need changing
	    serve("/viveladietetique/dispatch").with(AuthDispatchServiceServlet.class);
	}

}