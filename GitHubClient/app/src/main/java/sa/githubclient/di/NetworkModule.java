package sa.githubclient.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sa.githubclient.utils.NetworkUtils;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    NetworkUtils providesNetworkUtils(Context context){
        return new NetworkUtils(context);
    }
}