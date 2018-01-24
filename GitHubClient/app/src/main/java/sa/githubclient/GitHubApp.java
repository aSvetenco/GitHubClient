package sa.githubclient;

import android.app.Application;

import sa.githubclient.di.AppComponent;
import sa.githubclient.di.AppModule;
import sa.githubclient.di.DaggerAppComponent;

public class GitHubApp extends Application {

    private static AppComponent appComponent;
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    protected void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this)).build();
        appComponent.inject(this);
    }
}
