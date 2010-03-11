package fr.viveladietetique.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

import fr.viveladietetique.client.gin.GreetingGinjector;
import fr.viveladietetique.client.mvp.AppPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ViveLaDietetique implements EntryPoint
{
    private final GreetingGinjector injector = GWT.create(GreetingGinjector.class);

    public void onModuleLoad() {
        
        final AppPresenter appPresenter = injector.getAppPresenter();
        appPresenter.go(RootPanel.get("dataContent"));

        injector.getPlaceManager().fireCurrentPlace();
    }
}
