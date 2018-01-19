package sa.githubclient.screens.main.dagger;


import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.api.Api;
import sa.githubclient.screens.main.mvp.MainModel;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.SharedPref;
import sa.githubclient.utils.rx.RxSchedulers;

@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    MainPresenter providesMainPresenter(RxSchedulers rxSchedulers, MainModel model, NetworkUtils networkUtils) {
        return new MainPresenter(rxSchedulers, new CompositeSubscription(), model, networkUtils);
    }

    @MainActivityScope
    @Provides
    MainModel providesMainModel(SharedPref sharedPref, Api api) {
        return new MainModel(sharedPref, api);
    }

}
