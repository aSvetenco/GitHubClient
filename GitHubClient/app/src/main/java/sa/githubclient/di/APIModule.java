package sa.githubclient.di;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import sa.githubclient.api.Api;
import sa.githubclient.utils.rx.RxSchedulers;

@Module
public class APIModule {

  private static final String BASE_URL = "https://api.github.com";

  @Singleton
  @Provides
  public Retrofit providesRetrofit(RxSchedulers rxSchedulers, Gson gson) {
    return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(rxSchedulers
      .network()))
      .addConverterFactory(GsonConverterFactory.create(gson))
      .baseUrl(BASE_URL)
      .build();
  }

  @Singleton
  @Provides
  public Api providesProductApi(Retrofit retrofit) {
    return retrofit.create(Api.class);
  }

}
