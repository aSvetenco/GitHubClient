package sa.githubclient.screens.repos.mvp;

import java.util.List;

import sa.githubclient.api.models.Repository;

public interface ReposView {

    void showError(int resId);
    void showError(String string);
    void hideLoading();
    void showLoading();
    void showRepos(List<Repository> repositories);

}
