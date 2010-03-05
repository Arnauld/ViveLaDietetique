package fr.viveladietetique.client.gin;

import net.customware.gwt.presenter.client.place.PlaceManager;
import fr.viveladietetique.client.mvp.AppPresenter;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules({ GreetingClientModule.class })
public interface GreetingGinjector extends Ginjector {

	AppPresenter getAppPresenter();

	PlaceManager getPlaceManager();

}