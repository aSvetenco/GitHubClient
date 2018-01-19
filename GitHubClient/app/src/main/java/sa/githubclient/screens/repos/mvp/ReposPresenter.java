package sa.githubclient.screens.repos.mvp;

import android.util.Log;

import retrofit2.HttpException;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.R;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.exceptions.NoNetworkException;
import sa.githubclient.utils.rx.RxSchedulers;

public class ReposPresenter {

    private static final String TAG = ReposPresenter.class.getSimpleName();
    private final RxSchedulers rxSchedulers;
    private final CompositeSubscription subscription;
    private final ReposModel model;
    private final NetworkUtils networkUtils;
    private ReposView view;

    public ReposPresenter(RxSchedulers rxSchedulers,
                          CompositeSubscription compositeSubscription,
                          ReposModel model,
                          NetworkUtils networkUtils) {
        this.rxSchedulers = rxSchedulers;
        this.subscription = compositeSubscription;
        this.model = model;
        this.networkUtils = networkUtils;
    }

    public void takeView(ReposView view) {
        this.view = view;
        subscription.add(registerGetRepositoriesFromNetwork());
    }

    private Subscription registerGetRepositoriesFromNetwork(){
        return  networkUtils.networkAvailableWithException()
                        .observeOn(rxSchedulers.androidUI())
                        .onErrorResumeNext(throwable -> {
                            handleThrowable(throwable);
                            return Observable.never();
                        })
                .filter(isNetworkAvailable -> isNetworkAvailable)
                .observeOn(rxSchedulers.network())
                .flatMap(aBoolean -> model.getUserRepository()
                .onErrorResumeNext(throwable -> {
                    handleThrowable(throwable);
                    return Observable.never();
                }))
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
                            view.showRepos(repositories);
                        },
                        throwable -> Log.d(TAG, "GET repos fail", throwable));
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
