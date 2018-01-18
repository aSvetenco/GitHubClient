package sa.githubclient.di;

import android.content.Context;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sa.githubclient.utils.SharedPref;


@Module
public class StorageModule {
    private static final String GIT_HUB_CLIENT_PREFERENCES = "git_hub_client";

    @Singleton
    @Provides
    SharedPref providesSharedPref(Context context) {
        return new SharedPref(context.getSharedPreferences(GIT_HUB_CLIENT_PREFERENCES, Context.MODE_PRIVATE));
    }
}
