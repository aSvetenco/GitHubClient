package sa.githubclient.screens.repos.dagger;


import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.screens.repos.mvp.ReposModel;
import sa.githubclient.screens.repos.mvp.ReposPresenter;
import sa.githubclient.utils.SharedPref;
import sa.githubclient.utils.rx.RxSchedulers;


@Module
public class ReposActivityModule {

    @ReposActivityScope
    @Provides
    ReposPresenter providesLoginPresenter(RxSchedulers rxSchedulers, ReposModel model) {
        return new ReposPresenter(rxSchedulers, new CompositeSubscription(), model);
    }

    @ReposActivityScope
    @Provides
    ReposModel providesLoginModel(SharedPref sharedPref) {
        return  new ReposModel(sharedPref);
    }




}
