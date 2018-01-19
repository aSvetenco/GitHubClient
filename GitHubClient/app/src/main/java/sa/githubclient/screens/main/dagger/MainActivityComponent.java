package sa.githubclient.screens.main.dagger;


import dagger.Component;
import sa.githubclient.di.AppComponent;
import sa.githubclient.screens.main.MainActivity;


@Component(dependencies = {AppComponent.class}, modules = {MainActivityModule.class})
@MainActivityScope
public interface MainActivityComponent {
    void inject(MainActivity activity);
}

