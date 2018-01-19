package sa.githubclient.screens.repos.mvp;

import java.util.List;

import sa.githubclient.api.Api;
import sa.githubclient.api.models.Repository;

public class ReposModel {
    private final Api api;
    private String userName;

    public ReposModel(Api api, String userName) {
        this.api = api;
        this.userName = userName;
    }

    public rx.Observable<List<Repository>> getUserRepository() {
        return api.getRepositories(userName);
    }
}
