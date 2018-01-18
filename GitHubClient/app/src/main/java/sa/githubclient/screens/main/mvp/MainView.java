package sa.githubclient.screens.main.mvp;

import rx.Observable;

public interface MainView {

    Observable<CharSequence> getUserName();
    Observable<CharSequence> getPassword();
    Observable<Void> onContinueClick();
    void showMainActivity();
    void showError(int resId);


}
