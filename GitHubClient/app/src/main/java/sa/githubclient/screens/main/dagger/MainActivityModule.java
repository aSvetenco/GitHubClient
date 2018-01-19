package sa.githubclient.screens.main.dagger;


import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.api.Api;
import sa.githubclient.screens.main.mvp.MainModel;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.utils.NetworkUtils;
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
    MainModel providesMainModel(Api api) {
        return new MainModel(api);
    }

}
