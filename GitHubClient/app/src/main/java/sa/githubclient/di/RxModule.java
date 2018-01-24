package sa.githubclient.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sa.githubclient.utils.rx.AppRxSchedulers;
import sa.githubclient.utils.rx.RxSchedulers;


@Module
public class RxModule {

    @Singleton
    @Provides
    RxSchedulers provideRxSchedule() {
        return new AppRxSchedulers();
    }

}
