package sa.githubclient.screens.main.dagger;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.subscriptions.CompositeSubscription;
import sa.githubclient.api.Api;
import sa.githubclient.screens.main.MainActivity;
import sa.githubclient.screens.main.mvp.MainModel;
import sa.githubclient.screens.main.mvp.MainPresenter;
import sa.githubclient.utils.NetworkUtils;
import sa.githubclient.utils.SharedPref;
import sa.githubclient.utils.rx.RxSchedulers;

import static sa.githubclient.screens.main.MainActivity.PASSWORD_KEY;
import static sa.githubclient.screens.main.MainActivity.USER_NAME_KEY;

@Module
public class MainActivityModule {

    private static final String BASE_URL = "https://api.github.com";
    private static final String BASIC_AUTHORIZATION = "Basic ";
    private static final String AUTH_STRING = "auth_string";
    private static final String USER_NAME = "user_name";
    private static final String USER_PASSWORD = "user_password";

    private MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @MainActivityScope
    @Provides
    MainPresenter providesLoginPresenter(RxSchedulers rxSchedulers, MainModel model, NetworkUtils networkUtils) {
        return new MainPresenter(rxSchedulers, new CompositeSubscription(), model, networkUtils);
    }

    @MainActivityScope
    @Provides
    MainActivity providesActivity() {
        return activity;
    }

    @MainActivityScope
    @Provides
    MainModel providesLoginModel(SharedPref sharedPref, Api api) {
        return new MainModel(sharedPref, api);
    }

    @Provides
    @Named(USER_NAME)
    @MainActivityScope
    String providesUserName(MainActivity activity) {
        return activity.getIntent().getStringExtra(USER_NAME_KEY);
    }

    @Provides
    @Named(USER_PASSWORD)
    @MainActivityScope
    String providesPassword(MainActivity activity) {
        return activity.getIntent().getStringExtra(PASSWORD_KEY);
    }

    @MainActivityScope
    @Provides
    @Named(AUTH_STRING)
    String createAuthorizationString(@Named(USER_NAME) String userName, @Named(USER_PASSWORD) String password) {
        String combinedStr = String.format("%1$s:%2$s", userName, password);
        String authorizationString = Credentials.basic(userName, password); //BASIC_AUTHORIZATION + Base64.encodeToString(combinedStr.getBytes(), Base64.DEFAULT);
        return authorizationString;
    }

    @MainActivityScope
    @Provides
    OkHttpClient providesOkHttpClient(@Named(AUTH_STRING) String authorizationString) {
        return new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("Authorization", authorizationString)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }).build();
    }

    @MainActivityScope
    @Provides
    Retrofit providesRetrofit(RxSchedulers rxSchedulers, Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(rxSchedulers
                .network()))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                //.client(okHttpClient)
                .build();
    }

    @MainActivityScope
    @Provides
    Gson gson() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
    }

    @MainActivityScope
    @Provides
    Api providesApi(Retrofit retrofit) {
        return retrofit.create(Api.class);
    }


}
