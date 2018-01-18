package sa.githubclient.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import sa.githubclient.GitHubApp;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.rx.RxSchedulers;


@Component(modules = {AppModule.class, RxModule.class, NetworkModule.class})
@Singleton
public interface AppComponent {
    
    Context context();

    RxSchedulers rxSchedulers();

    NetworkUtils networkUtils();

    void inject(GitHubApp enfApp);

}
