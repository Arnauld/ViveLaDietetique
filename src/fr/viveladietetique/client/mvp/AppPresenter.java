package fr.viveladietetique.client.mvp;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.inject.Inject;

public class AppPresenter {
	private HasWidgets container;
	private GreetingHomePresenter greetingPresenter;

	@Inject
	public AppPresenter(final DispatchAsync dispatcher,
						final GreetingHomePresenter greetingPresenter) {
		this.greetingPresenter = greetingPresenter;		
	}
	
	public void go(final HasWidgets container) {
	    this.container = container;
	    showMain();
	}
	
	private void showMain() {
		container.clear();
		container.add(greetingPresenter.getDisplay().asWidget());
	}
		
}