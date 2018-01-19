package sa.githubclient.screens.repos.mvp;

import sa.githubclient.utils.SharedPref;

public class ReposModel {
    private final SharedPref sharedPref;

    public ReposModel(SharedPref sharedPref) {
        this.sharedPref = sharedPref;
    }
}
