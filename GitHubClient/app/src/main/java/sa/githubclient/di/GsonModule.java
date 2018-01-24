package sa.githubclient.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class GsonModule {

  @Singleton
  @Provides
  Gson gson() {
    return new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();
  }
}