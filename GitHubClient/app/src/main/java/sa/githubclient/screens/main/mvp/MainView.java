package sa.githubclient.screens.main.mvp;

import rx.Observable;
import sa.githubclient.api.models.User;

public interface MainView {

    Observable<String> onSearchClick();
    Observable<Void> onSeeUserReposClick();
    void showError(int resId);
    void showError(String string);
    void hideLoading();
    void showLoading();
    void showUser(User user);
    void startRepoActivity(String userName);

}
