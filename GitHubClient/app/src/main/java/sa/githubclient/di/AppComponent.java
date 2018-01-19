package sa.githubclient.di;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import sa.githubclient.GitHubApp;
import sa.githubclient.api.Api;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.rx.RxSchedulers;


@Component(modules = {AppModule.class, RxModule.class, NetworkModule.class,
        GsonModule.class, APIModule.class})
@Singleton
public interface AppComponent {
    
    Context context();

    RxSchedulers rxSchedulers();

    NetworkUtils networkUtils();

    Gson gson();

    Api api();

    void inject(GitHubApp enfApp);

}
