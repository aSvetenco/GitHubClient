package sa.githubclient.screens.main.mvp;

import sa.githubclient.utils.SharedPref;

public class MainModel {
    private final SharedPref sharedPref;

    public MainModel(SharedPref sharedPref) {
        this.sharedPref = sharedPref;
    }
}
