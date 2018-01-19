package sa.githubclient.screens.main.mvp;

import rx.Observable;
import sa.githubclient.api.Api;
import sa.githubclient.api.models.User;

public class MainModel {

    private final Api api;

    public MainModel(Api api) {
        this.api = api;
    }

    public Observable<User> getUserPublicProfile(String userName) {
        return api.getUserPublicProfile(userName);
    }

}
