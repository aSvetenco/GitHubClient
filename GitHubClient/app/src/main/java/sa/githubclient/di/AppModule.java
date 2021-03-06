package sa.githubclient.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(Context context) {
        this.appContext = context;
    }

    @Singleton
    @Provides
    Context providesAppContext() {
        return appContext;
    }

}
