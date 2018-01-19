package sa.githubclient.screens.main.mvp;

import android.util.Log;

import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.R;
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
        subscription.add(registerOnSeeUserReposClick());
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
                .filter(isNetworkAvailable -> isNetworkAvailable)
                .observeOn(rxSchedulers.network())
                .flatMap(aBoolean -> model.getUserPublicProfile(userName)
                        .observeOn(rxSchedulers.androidUI())
                        .onErrorResumeNext(throwable -> {
                            handleThrowable(throwable);
                            return Observable.never();
                        }))
                .observeOn(rxSchedulers.androidUI())
                .doOnNext(user -> {
                    if (user == null) {
                        view.hideLoading();
                        view.showError(R.string.user_dosent_exist);
                    }
                })
                .filter(user -> user != null)
                .subscribe(user -> {
                            view.hideLoading();
                            view.showUser(user);
                        },
                        throwable -> Log.d(TAG, "GET user info fail", throwable));
    }

    private Subscription registerOnSeeUserReposClick() {
        return view.onSeeUserReposClick()
                .subscribe(aVoid -> view.startRepoActivity(userName));
    }

    private void handleThrowable(Throwable throwable) {
        view.hideLoading();
        if (throwable instanceof NoNetworkException) {
            view.showError(R.string.error_no_network);
        } else if (throwable instanceof HttpException) {
            view.showError(((HttpException) throwable).message());
        } else {
            view.showError(R.string.error_general);
        }
    }

    public void detachView() {
        subscription.clear();
    }

}
