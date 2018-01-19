package sa.githubclient.screens.main.mvp;

import java.util.List;

import rx.Observable;
import sa.githubclient.api.models.User;

public interface MainView {

    Observable<String> onSearchClick();
    void showError(int resId);
    void showError(String string);
    void hideLoading();
    void showLoading();
    void showUser(User user);
    void showRepos(List<Repository> repositories);

}
