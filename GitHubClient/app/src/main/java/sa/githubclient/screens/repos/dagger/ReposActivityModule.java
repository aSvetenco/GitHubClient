package sa.githubclient.screens.repos.dagger;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.api.Api;
import sa.githubclient.screens.repos.ReposActivity;
import sa.githubclient.screens.repos.ReposRVAdapter;
import sa.githubclient.screens.repos.mvp.ReposModel;
import sa.githubclient.screens.repos.mvp.ReposPresenter;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.rx.RxSchedulers;

import static sa.githubclient.screens.repos.ReposActivity.USER_NAME_KEY;


@Module
public class ReposActivityModule {

    public static final String USER_NAME = "user_name";
    private final ReposActivity activity;

    public ReposActivityModule(ReposActivity activity) {
        this.activity = activity;
    }

    @ReposActivityScope
    @Provides
    ReposPresenter providesLoginPresenter(RxSchedulers rxSchedulers, ReposModel model, NetworkUtils networkUtils) {
        return new ReposPresenter(rxSchedulers, new CompositeSubscription(), model, networkUtils);
    }

    @ReposActivityScope
    @Provides
    ReposModel providesLoginModel(Api api, @Named(USER_NAME) String user_name) {
        return  new ReposModel(api, user_name);
    }

    @ReposActivityScope
    @Provides
    ReposRVAdapter providesReposRVAdapter() {
        return  new ReposRVAdapter();
    }

    @ReposActivityScope
    @Provides
    ReposActivity providesReposActivity() {
        return activity;
    }

    @ReposActivityScope
    @Named(USER_NAME)
    @Provides
    String providesUserName(ReposActivity activity) {
        return activity.getIntent().getStringExtra(USER_NAME_KEY);
    }

}
