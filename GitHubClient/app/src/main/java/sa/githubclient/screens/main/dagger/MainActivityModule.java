package sa.githubclient.screens.main.dagger;


import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.screens.main.mvp.MainModel;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.utils.SharedPref;
import sa.githubclient.utils.rx.RxSchedulers;


@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    MainPresenter providesLoginPresenter(RxSchedulers rxSchedulers, MainModel model) {
        return new MainPresenter(rxSchedulers, new CompositeSubscription(), model);
    }

    @MainActivityScope
    @Provides
    MainModel providesLoginModel(SharedPref sharedPref) {
        return  new MainModel(sharedPref);
    }




}
