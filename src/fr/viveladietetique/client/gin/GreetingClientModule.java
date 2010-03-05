package fr.viveladietetique.client.gin;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.presenter.client.DefaultEventBus;
import net.customware.gwt.presenter.client.EventBus;
import net.customware.gwt.presenter.client.gin.AbstractPresenterModule;
import net.customware.gwt.presenter.client.place.PlaceManager;

import com.google.inject.Singleton;

import fr.viveladietetique.client.dispatch.AuthDispatchAsync;
import fr.viveladietetique.client.mvp.AppPresenter;
import fr.viveladietetique.client.mvp.GreetingHomePresenter;
import fr.viveladietetique.client.mvp.GreetingHomeView;
import fr.viveladietetique.client.mvp.GreetingResponsePresenter;
import fr.viveladietetique.client.mvp.GreetingResponseView;

public class GreetingClientModule extends AbstractPresenterModule
{
    @Override
    protected void configure()
    {
        // bind(CachingDispatchAsync.class);
        // use this class instead of gwt-presenter’s Standard or
        // SecureDispatchAsync,
        // simply modify the binding in your GIN module:
        bind(DispatchAsync.class).to(AuthDispatchAsync.class).in(Singleton.class);

        //
        bind(EventBus.class).to(DefaultEventBus.class).in(Singleton.class);
        bind(PlaceManager.class).in(Singleton.class);

        bindPresenter(GreetingHomePresenter.class, GreetingHomePresenter.Display.class, GreetingHomeView.class);
        bindPresenter(GreetingResponsePresenter.class, GreetingResponsePresenter.Display.class, GreetingResponseView.class);

        bind(AppPresenter.class).in(Singleton.class);

    }
}