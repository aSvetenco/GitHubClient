package sa.githubclient.screens.main.mvp;

import android.util.Log;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.R;
import sa.githubclient.api.models.User;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.exceptions.NoNetworkException;
import sa.githubclient.utils.rx.RxSchedulers;

public class MainPresenter {

    private static final String TAG = MainPresenter.class.getSimpleName();
    private final RxSchedulers rxSchedulers;
    private final CompositeSubscription subscription;
    private final MainModel model;
    private final NetworkUtils networkUtils;
    private String userName;
    private MainView view;
    private User userProfile;



    public MainPresenter(RxSchedulers rxSchedulers,
                         CompositeSubscription compositeSubscription,
                         MainModel model,
                         NetworkUtils networkUtils) {
        this.rxSchedulers = rxSchedulers;
        this.subscription = compositeSubscription;
        this.model = model;
        this.networkUtils = networkUtils;
    }

    public void takeView(MainView view) {
        this.view = view;
        subscription.add(registerOnSearchQueryChangedSubscription());
    }

    private Subscription registerOnSearchQueryChangedSubscription() {
        return view.onSearchClick()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> view.showLoading())
                .doOnNext(s -> this.userName = s)
                .flatMap(s -> networkUtils.networkAvailableWithException()
                        .observeOn(rxSchedulers.androidUI())
                        .onErrorResumeNext(throwable -> {
                            handleThrowable(throwable);
                            return Observable.never();
                        }))
               // .filter(isNetworkAvailable -> !isNetworkAvailable)
                .observeOn(rxSchedulers.network())
                .flatMap(aBoolean -> model.getUserPublicProfile(userName))
                .observeOn(rxSchedulers.androidUI())
                .doOnNext(user -> {
                    if (user == null) {
                        view.hideLoading();
                        view.showError(R.string.user_dosent_exist);
                    }
                })
                .filter(user -> user != null)
                .doOnNext(user -> userProfile = user)
                .flatMap(user -> networkUtils.networkAvailableWithException()
                        .observeOn(rxSchedulers.androidUI())
                        .onErrorResumeNext(throwable -> {
                            handleThrowable(throwable);
                            return Observable.never();
                        }))
                //.filter(isNetworkAvailable -> !isNetworkAvailable)
                .observeOn(rxSchedulers.network())
                .flatMap(aBoolean -> model.getUserRepo(userName))
                .observeOn(rxSchedulers.androidUI())
                .doOnNext(repositories -> {
                    if (repositories == null || repositories.isEmpty()) {
                        view.hideLoading();
                        view.showError(R.string.user_doest_have_repo);
                    }
                })
                .filter(repositories -> !(repositories == null || repositories.isEmpty()))
                .subscribe(repositories -> {
                    view.hideLoading();
                    view.showUser(userProfile);
                    view.showRepos(repositories);
                },
                        throwable -> Log.d(TAG, "GET user info fail", throwable));

    }


    private void handleThrowable(Throwable throwable) {
        view.hideLoading();
        if (throwable instanceof NoNetworkException) {
            view.showError(R.string.error_no_network);
        } else {
            view.showError(R.string.error_general);
        }
    }

}
