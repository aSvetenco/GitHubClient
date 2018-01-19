package sa.githubclient.screens.main.mvp;

import java.util.List;

import rx.Observable;
import sa.githubclient.api.Api;
import sa.githubclient.api.models.Repository;
import sa.githubclient.api.models.User;
import sa.githubclient.utils.SharedPref;

public class MainModel {

    private final SharedPref sharedPref;
    private final Api api;


    public MainModel(SharedPref sharedPref, Api api) {
        this.sharedPref = sharedPref;
        this.api = api;
    }

    public Observable<List<Repository>> getUserRepo(String userName) {
       return api.getRepositories(userName);
    }

    public Observable<User> getUserPublicProfile(String userName) {
        return api.getUserPublicProfile(userName);
    }

}
