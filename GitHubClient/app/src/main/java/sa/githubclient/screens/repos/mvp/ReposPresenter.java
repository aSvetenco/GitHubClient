package sa.githubclient.screens.repos.mvp;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.R;
import sa.githubclient.utils.rx.RxSchedulers;

public class ReposPresenter {

    private static final String TAG = ReposPresenter.class.getSimpleName();
    private static final long DEBOUNCE_TIME = 300;
    private final RxSchedulers rxSchedulers;
    private final CompositeSubscription subscription;
    private final ReposModel model;
    private ReposView view;
    private String userName;
    private String password;

    public ReposPresenter(RxSchedulers rxSchedulers, CompositeSubscription compositeSubscription, ReposModel model) {
        this.rxSchedulers = rxSchedulers;
        this.subscription = compositeSubscription;
        this.model = model;
    }

    public void takeView(ReposView view) {
        this.view = view;
        subscription.add(getUserName());
        subscription.add(getUserPassword());
        subscription.add(registerOnContinueClick());
    }


    private Subscription getUserName() {
        return view.getUserName()
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(rxSchedulers.androidUI())
                .subscribe(charSequence -> userName = charSequence.toString());
    }

    private Subscription getUserPassword() {
        return view.getPassword()
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .observeOn(rxSchedulers.androidUI())
                .subscribe(charSequence -> password = charSequence.toString());
    }

    private Subscription registerOnContinueClick() {
        return view.onContinueClick()
                .map(aVoid -> {
                    if (userName.isEmpty()) {
                        view.showUserNameError(R.string.user_name_empty);
                        return false;
                    }
                    return true;
                })
                .filter(aBoolean -> aBoolean)
                .map(aVoid -> {
                    if (password.isEmpty()) {
                        view.showPasswordError(R.string.password_empty);
                        return false;
                    }
                    return true;
                })
                .filter(aBoolean -> aBoolean)
                .subscribe(aBoolean -> view.showMainActivity(userName, password),
                        throwable -> Log.d(TAG, throwable.getMessage(), throwable)
                );
    }


    public void deatachView() {
        subscription.clear();
    }
}
