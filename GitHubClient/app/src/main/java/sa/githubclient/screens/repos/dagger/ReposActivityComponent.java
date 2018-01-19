package sa.githubclient.screens.repos.dagger;


import dagger.Component;
import sa.githubclient.di.AppComponent;
import sa.githubclient.screens.repos.ReposActivity;


@Component(dependencies = {AppComponent.class}, modules = {ReposActivityModule.class})
@ReposActivityScope
public interface ReposActivityComponent {
    void inject(ReposActivity activity);
}

